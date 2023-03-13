package com.poli.internship.domain.usecase;

import com.poli.internship.data.datasource.CurriculumDataSource;
import com.poli.internship.domain.models.CurriculumModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.poli.internship.domain.models.CurriculumModel.CurriculumInput;

@Service
public class UpdateCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public CurriculumModel.Curriculum exec(CurriculumInput curriculumInput, String id){
        return this.dataSource.updateCurriculum(curriculumInput, Long.parseLong(id));
    }
}
