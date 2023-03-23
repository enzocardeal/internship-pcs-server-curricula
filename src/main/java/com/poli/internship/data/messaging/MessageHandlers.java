package com.poli.internship.data.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.poli.internship.api.error.CustomError;
import com.poli.internship.data.entity.CurriculumAuthorizationEntity;
import com.poli.internship.data.repository.CurriculumAuthorizationRepository;
import com.poli.internship.domain.models.CurriculumAuthorizationActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MessageHandlers {
    @Autowired
    private CurriculumAuthorizationRepository curriculumAuthRepository;
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            String messagePayloadString = new String((byte[]) message.getPayload());

            try {
                HashMap<String, Object> userInfo = new ObjectMapper().readValue(messagePayloadString, HashMap.class);

                CurriculumAuthorizationActionType action = CurriculumAuthorizationActionType.valueOf((String) userInfo.get("action"));
                if(action == CurriculumAuthorizationActionType.GRANT) {
                    CurriculumAuthorizationEntity curriculumAuth = new CurriculumAuthorizationEntity(
                            Long.parseLong((String) userInfo.get("companyId")),
                            Long.parseLong((String) userInfo.get("studentId"))
                    );

                    this.curriculumAuthRepository.save(curriculumAuth);
                }
                if(action == CurriculumAuthorizationActionType.REVOKE) {
                    CurriculumAuthorizationEntity existingCurriculumAuth = this.curriculumAuthRepository.findByCompanyIdAndStudentId(
                            Long.parseLong((String) userInfo.get("companyId")),
                            Long.parseLong((String) userInfo.get("studentId"))
                    );

                    this.curriculumAuthRepository.delete(existingCurriculumAuth);
                }

            } catch (Exception e) {
                // Log exception when we have a logger
            }


            BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
        };
    }
}
