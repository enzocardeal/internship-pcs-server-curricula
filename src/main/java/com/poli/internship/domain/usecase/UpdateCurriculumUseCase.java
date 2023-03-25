package com.poli.internship.domain.usecase;

import com.poli.internship.api.error.CustomError;
import com.poli.internship.data.datasource.CurriculumDataSource;
import static com.poli.internship.domain.models.CurriculumModel.Curriculum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import static com.poli.internship.domain.models.CurriculumModel.CurriculumInput;

@Service
public class UpdateCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public Curriculum exec(CurriculumInput curriculumInput){
        Curriculum curriculum = this.dataSource.getCurriculumByUserId(curriculumInput.userId());
        if(curriculum != null) {
            return this.dataSource.updateCurriculum(curriculumInput, Long.parseLong(curriculum.id()));
        }

        throw new CustomError("There's no curriculum to be updated.", ErrorType.BAD_REQUEST);
    }
}
