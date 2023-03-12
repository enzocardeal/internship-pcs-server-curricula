package com.poli.internship.domain.usecase;

import com.poli.internship.data.datasource.CurriculumDataSource;
import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.poli.internship.domain.models.CurriculumModel.CurriculumInput;
import static com.poli.internship.domain.models.CurriculumModel.Curriculum;

import java.time.LocalDate;
import java.util.Set;

@Service
public class CreateCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public Curriculum exec(CurriculumInput curriculumInput){
        return this.dataSource.createCurriculum(curriculumInput);
    }
}
