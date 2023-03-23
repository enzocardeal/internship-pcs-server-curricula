package com.poli.internship;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import com.poli.internship.data.entity.CurriculumAuthorizationEntity;
import com.poli.internship.data.repository.CurriculumAuthorizationRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurriculumAuthorizationTest {
    @Value("${spring.cloud.gcp.pubsub.emulator-host}")
    private String pubsubEmulatorHostport;
    private ManagedChannel channel;
    private Publisher publisher;
    private TopicAdminClient topicClient;

    @Autowired
    private CurriculumAuthorizationRepository curriculumAuthorizationRepository;
    @BeforeAll
    void beforeAll() {
        this.curriculumAuthorizationRepository.deleteAll();

        channel = ManagedChannelBuilder.forTarget(pubsubEmulatorHostport).usePlaintext().build();
        TransportChannelProvider channelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel));
        CredentialsProvider credentialsProvider = NoCredentialsProvider.create();

        try {

        topicClient =
                    TopicAdminClient.create(
                            TopicAdminSettings.newBuilder()
                                    .setTransportChannelProvider(channelProvider)
                                    .setCredentialsProvider(credentialsProvider)
                                    .build());

        TopicName topicName = TopicName.of("PROJECT_TEST_ID", "curriculum-authorization");

        publisher = Publisher.newBuilder(topicName)
                            .setChannelProvider(channelProvider)
                            .setCredentialsProvider(credentialsProvider)
                            .build();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to setup pub sub test environment.");
        }

    }

    @AfterAll
    void afterAll() {
        channel.shutdown();
        this.curriculumAuthorizationRepository.deleteAll();

    }
    @Test
    void shouldGrantAuthorization() {
        String jsonString = "{\"action\": \"GRANT\", \"studentId\": \"123\", \"companyId\": \"456\"}";
        PubsubMessage message = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(jsonString)).build();
        publisher.publish(message);

        try {
            topicClient.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e);
        }

        CurriculumAuthorizationEntity curriculumAuth = curriculumAuthorizationRepository.findByCompanyIdAndStudentId(456, 123);

        assertThat(curriculumAuth).isNotNull();
    }

    @Test
    void shouldRevokeAuthorization() {
        curriculumAuthorizationRepository.save(new CurriculumAuthorizationEntity(456l, 123l));

        String jsonString = "{\"action\": \"REVOKE\", \"studentId\": \"123\", \"companyId\": \"456\"}";
        PubsubMessage message = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(jsonString)).build();
        publisher.publish(message);

        try {
            topicClient.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e);
        }

        CurriculumAuthorizationEntity curriculumAuth = curriculumAuthorizationRepository.findByCompanyIdAndStudentId(456, 123);

        assertThat(curriculumAuth).isNull();
    }


}
