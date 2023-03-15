package com.poli.internship;

import com.poli.internship.api.context.JWTService;
import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import com.poli.internship.data.entity.CurriculumEntity;
import com.poli.internship.data.repository.CurriculumRepository;
import com.poli.internship.domain.models.AuthTokenPayloadModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@ActiveProfiles("test")
public class CurriculumTest {
    @Autowired
    private HttpGraphQlTester tester;
    @Autowired
    private CurriculumRepository repository;
    @Autowired
    private JWTService jwtService;

    HttpGraphQlTester testerWithAuth;
    AuthTokenPayloadModel.AuthTokenPayload tokenPayload;
    String authToken;

    @BeforeEach
    public void beforeEach() {
        tokenPayload = new AuthTokenPayloadModel.AuthTokenPayload(
                "123",
                "enzo@teste.com",
                3600);
        authToken = this.jwtService.createAuthorizationToken(tokenPayload);
        testerWithAuth = this.tester.mutate().header("Authorization", authToken).build();
    }

    @AfterEach
    public void afterEach(){
        this.repository.deleteAll();
    }

    @Test
    @Transactional
    public void createAndGetCurriculum(){
        Map<String, Object> createInput = new HashMap<String, Object>();
        List<Object> certificates = new ArrayList<Object>();
        certificates.add(
                Map.of(
                     "name", "DS for dummies",
                     "description", "",
                     "completedAt", "2021-05-03",
                     "expiresAt", "2025-05-03"
                )
        );
        List<Object> pastExperiences = new ArrayList<Object>();
        pastExperiences.add(
                Map.of(
                       "company", "BTG Pactual",
                       "role", "IT Finance Intern",
                       "description", "",
                       "startedAt", "2021-05-01",
                       "endedAt", "2021-08-30"
                )
        );
        createInput.put("name", "Enzo");
        createInput.put("lastName", "Neves");
        createInput.put("degreeCourse", "Engenharia da Computação");
        createInput.put("graduationYear", "2023-12-30");
        createInput.put("pastExperiences", pastExperiences);
        createInput.put("certificates", certificates);

        Curriculum curriculumCreated = testerWithAuth.documentName("createCurriculum")
                .variable("input", createInput)
                .execute()
                .path("createCurriculum")
                .entity(Curriculum.class)
                .get();

        Curriculum curriculumReturned = testerWithAuth.documentName("getCurriculum")
                .execute()
                .path("getCurriculum")
                .entity(Curriculum.class)
                .get();

        ExperienceEmbeddable[] pastExperiencesCreated = curriculumCreated.pastExperiences().toArray(new ExperienceEmbeddable[curriculumCreated.pastExperiences().size()]);
        ExperienceEmbeddable[] pastExperiencesReturned = curriculumReturned.pastExperiences().toArray(new ExperienceEmbeddable[curriculumReturned.pastExperiences().size()]);

        ActivityEmbeddable[] certificatesCreated = curriculumCreated.certificates().toArray(new ActivityEmbeddable[curriculumCreated.certificates().size()]);
        ActivityEmbeddable[] certificatesReturned = curriculumReturned.certificates().toArray(new ActivityEmbeddable[curriculumReturned.certificates().size()]);

        assertThat(curriculumCreated.id()).isNotNull();
        assertThat(curriculumCreated.id()).isEqualTo(curriculumReturned.id());
        assertThat(curriculumCreated.name()).isEqualTo(curriculumReturned.name());
        assertThat(curriculumCreated.name()).isEqualTo(curriculumReturned.name());
        assertEquals(pastExperiencesCreated[0].getCompany(), pastExperiencesReturned[0].getCompany());
        assertEquals(pastExperiencesCreated[0].getRole(), pastExperiencesReturned[0].getRole());
        assertEquals(certificatesCreated[0].getName(), certificatesReturned[0].getName());
    }

    @Test
    @Transactional
    public void deleteCurriculum(){
        String id = createElementsOnDb().getId().toString();

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("id", id);

        Boolean deleted = testerWithAuth.documentName("deleteCurriculum")
                .variable("input", input)
                .execute()
                .path("deleteCurriculum")
                .entity(Boolean.class)
                .get();

        assertTrue(deleted);
    }

    @Test
    @Transactional
    public void updateCurriculum(){
        CurriculumEntity curriculumEntity = createElementsOnDb();
        String id = curriculumEntity.getId().toString();
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("id", id);
        input.put("graduationYear", LocalDate.parse("2024-12-30"));

        Curriculum curriculum = testerWithAuth.documentName("updateCurriculum")
                .variable("input", input)
                .execute()
                .path("updateCurriculum")
                .entity(Curriculum.class)
                .get();

        assertThat(curriculum.id().toString()).isEqualTo(curriculumEntity.getId().toString());
        assertThat(curriculum.graduationYear()).isEqualTo(LocalDate.parse("2024-12-30"));
    }

    @Test
    @Disabled
    @Transactional
    public void getAllCurricula(){
        CurriculumEntity curriculumEntity = createElementsOnDb();
        String id = curriculumEntity.getId().toString();
        Map<String, Object> input = new HashMap<String, Object>();

        List<HashMap> curricula = testerWithAuth.documentName("getAllCurricula")
                .execute()
                .path("getAllCurricula")
                .entity(List.class)
                .get();

        assertThat((String)curricula.get(0).get("id")).isEqualTo(id);
    }

    private CurriculumEntity createElementsOnDb(){
        Set<ExperienceEmbeddable> pastExperiences = new HashSet<ExperienceEmbeddable>();
        Set<ActivityEmbeddable> certificates = new HashSet<ActivityEmbeddable>();
        pastExperiences.add(
                new ExperienceEmbeddable(
                        "BTG Pactual",
                        "IT Finance Intern",
                        "",
                        LocalDate.parse("2021-05-01"),
                        LocalDate.parse("2021-08-30")
                )
        );
        pastExperiences.add(
                new ExperienceEmbeddable(
                        "BTG Pactual",
                        "IT Security Intern",
                        "",
                        LocalDate.parse("2022-01-01"),
                        LocalDate.parse("2022-04-28")
                )
        );
        certificates.add(
                new ActivityEmbeddable(
                       "ML for dummies",
                       "",
                       LocalDate.parse("2021-05-03"),
                       LocalDate.parse("2025-05-03")
                )
        );
        certificates.add(
                new ActivityEmbeddable(
                        "DS for dummies",
                        "",
                        LocalDate.parse("2021-05-03"),
                        LocalDate.parse("2025-05-03")
                )
        );
        CurriculumEntity curriculumEntity = this.repository.save(
                new CurriculumEntity(
                        123L,
                        "Enzo",
                        "Neves",
                        "Engenharia da Computação",
                        LocalDate.parse("2023-12-30"),
                        pastExperiences,
                        certificates
                )
        );

        return curriculumEntity;
    }
}
