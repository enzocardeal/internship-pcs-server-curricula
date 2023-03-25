package com.poli.internship.domain.usecase;

import com.poli.internship.api.error.CustomError;
import com.poli.internship.data.datasource.CurriculumDataSource;
import static com.poli.internship.domain.models.CurriculumModel.Curriculum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

@Service
public class DeleteCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public Boolean exec(String userId) {
        Curriculum curriculum = this.dataSource.getCurriculumByUserId(userId);
        if(curriculum != null) {
            return this.dataSource.deleteCurriculum(curriculum.id());
        }

        throw new CustomError("There's no curriculum to be deleted.", ErrorType.BAD_REQUEST);
    }
}
