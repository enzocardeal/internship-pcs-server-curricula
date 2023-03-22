package com.poli.internship.domain.usecase;

import com.poli.internship.api.error.CustomError;
import com.poli.internship.data.datasource.CurriculumDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import static com.poli.internship.domain.models.CurriculumModel.CurriculumInput;
import static com.poli.internship.domain.models.CurriculumModel.Curriculum;

@Service
public class CreateCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public Curriculum exec(CurriculumInput curriculumInput){
        Curriculum curriculum = this.dataSource.getCurriculumByUserId(curriculumInput.userId());
        if(curriculum != null) {
            throw new CustomError("Curriculum already exists.", ErrorType.BAD_REQUEST);
        }
        return this.dataSource.createCurriculum(curriculumInput);
    }
}
