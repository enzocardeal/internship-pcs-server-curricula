package com.poli.internship.domain.usecase;

import com.poli.internship.api.error.CustomError;
import com.poli.internship.data.datasource.CurriculumAuthorizationDataSource;
import com.poli.internship.data.datasource.CurriculumDataSource;
import com.poli.internship.data.entity.CurriculumAuthorizationEntity;
import com.poli.internship.data.repository.CurriculumAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;

@Service
public class GetCurriculumForCompanyUseCase {
    @Autowired
    private CurriculumDataSource curriculumDataSource;
    @Autowired
    private CurriculumAuthorizationDataSource curriculumAuthorizationDataSource;

    public Curriculum exec(String companyId, String studentId){
        boolean isCompanyAuthorized = this.curriculumAuthorizationDataSource.isCompanyAuthorizedToViewCurriculum(companyId, studentId);

        if(isCompanyAuthorized) {
            return this.curriculumDataSource.getCurriculumByUserId(studentId);
        }

        throw new CustomError("Company not authorized.", ErrorType.FORBIDDEN);
    }
}
