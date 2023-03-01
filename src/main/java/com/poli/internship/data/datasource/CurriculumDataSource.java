package com.poli.internship.data.datasource;

import com.poli.internship.data.entity.CurriculumEntity;
import com.poli.internship.data.mapper.CurriculumMapper;
import com.poli.internship.data.repository.CurriculumRepository;
import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.domain.models.CurriculumModel;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class CurriculumDataSource {
    @Autowired
    public CurriculumRepository repository;

    public CurriculumModel createCurriculum(String name,
                                            String lastName,
                                            String degreeCourse,
                                            LocalDate graduationYear,
                                            Set<ExperienceEmbeddable> pastExperiences,
                                            Set<ActivityEmbeddable> certificates)
    {
        CurriculumEntity curriculumEntity = repository.save(new CurriculumEntity(name,
                lastName,
                degreeCourse,
                graduationYear,
                pastExperiences,
                certificates));
        return CurriculumMapper.INSTANCE.curriculumEntityToModel(curriculumEntity);
    }
}
