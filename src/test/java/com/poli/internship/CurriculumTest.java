package com.poli.internship;

import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import com.poli.internship.data.entity.CurriculumEntity;
import com.poli.internship.data.repository.CurriculumRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
public class CurriculumTest {
    @Autowired
    private GraphQlTester tester;
    @Autowired
    private CurriculumRepository repository;

    @AfterEach
    public void afterEach(){
        this.repository.deleteAll();
    }

    @Test
    @Transactional
    public void createAndGetCurriculum(){
//        Set<ExperienceEmbeddable> pastExperiences = new HashSet<ExperienceEmbeddable>();
//        Set<ActivityEmbeddable> certificates = new HashSet<ActivityEmbeddable>();
//        pastExperiences.add(
//                new ExperienceEmbeddable(
//                        "BTG Pactual",
//                        "IT Finance Intern",
//                        "",
//                        LocalDate.parse("2021-05-01"),
//                        LocalDate.parse("2021-08-30")
//                )
//        );
//        pastExperiences.add(
//                new ExperienceEmbeddable(
//                        "BTG Pactual",
//                        "IT Security Intern",
//                        "",
//                        LocalDate.parse("2022-01-01"),
//                        LocalDate.parse("2022-04-28")
//                )
//        );
//        certificates.add(
//                new ActivityEmbeddable(
//                       "ML for dummies",
//                       "",
//                       LocalDate.parse("2021-05-03"),
//                       LocalDate.parse("2025-05-03")
//                )
//        );
//        certificates.add(
//                new ActivityEmbeddable(
//                        "DS for dummies",
//                        "",
//                        LocalDate.parse("2021-05-03"),
//                        LocalDate.parse("2025-05-03")
//                )
//        );
//        CurriculumEntity curriculumEntity = this.repository.save(
//                new CurriculumEntity(
//                        "Enzo",
//                        "Neves",
//                        "Engenharia da Computação",
//                        LocalDate.parse("2023-12-30"),
//                        pastExperiences,
//                        certificates
//                )
//        );
//
//        String id = curriculumEntity.getId().toString();

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
        certificates.add(
                Map.of(
                        "name", "ML for dummies",
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
        pastExperiences.add(
                Map.of(
                       "company", "BTG Pactual",
                       "role", "IT Security Intern",
                       "description", "",
                       "startedAt", "2022-01-01",
                       "endedAt", "2022-04-28"
                )
        );
        createInput.put("name", "Enzo");
        createInput.put("lastName", "Neves");
        createInput.put("degreeCourse", "Engenharia da Computação");
        createInput.put("graduationYear", "2023-12-30");
        createInput.put("pastExperiences", pastExperiences);
        createInput.put("certificates", certificates);

        Curriculum curriculumCreated = this.tester.documentName("createCurriculum")
                .variable("input", createInput)
                .execute()
                .path("createCurriculum")
                .entity(Curriculum.class)
                .get();

        Map<String, Object> getInput = new HashMap<String, Object>();
        getInput.put("id", curriculumCreated.id().toString());
        Curriculum curriculumReturned = this.tester.documentName("getCurriculumById")
                .variable("input", getInput)
                .execute()
                .path("getCurriculumById")
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
        assertEquals(pastExperiencesCreated[0].getCompany(), pastExperiencesReturned[1].getCompany());
        assertEquals(pastExperiencesCreated[0].getRole(), pastExperiencesReturned[1].getRole());
        assertEquals(certificatesCreated[0].getName(), certificatesReturned[0].getName());
        assertEquals(pastExperiencesCreated[1].getCompany(), pastExperiencesReturned[0].getCompany());
        assertEquals(pastExperiencesCreated[1].getRole(), pastExperiencesReturned[0].getRole());
        assertEquals(certificatesCreated[1].getName(), certificatesReturned[1].getName());
    }
}
