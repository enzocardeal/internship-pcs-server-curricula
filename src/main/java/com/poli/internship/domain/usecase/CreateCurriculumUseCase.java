package com.poli.internship.domain.usecase;

import com.poli.internship.data.datasource.CurriculumDataSource;
import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.domain.models.CurriculumModel;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class CreateCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public CurriculumModel exec(String name,
                                String lastName,
                                String degreeCourse,
                                LocalDate graduationYear,
                                Set<ExperienceEmbeddable> pastExperiences,
                                Set<ActivityEmbeddable> certificates){
        return this.dataSource.createCurriculum(name,
                lastName,
                degreeCourse,
                graduationYear,
                pastExperiences,
                certificates);
    }
}
