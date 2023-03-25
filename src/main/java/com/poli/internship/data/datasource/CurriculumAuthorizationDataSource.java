package com.poli.internship.data.datasource;

import com.poli.internship.data.entity.CurriculumAuthorizationEntity;
import com.poli.internship.data.repository.CurriculumAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurriculumAuthorizationDataSource {
    @Autowired
    private CurriculumAuthorizationRepository curriculumAuthorizationRepository;

    public boolean isCompanyAuthorizedToViewCurriculum(String companyId, String studentId) {
        return this.curriculumAuthorizationRepository.existsByCompanyIdAndStudentId(
                        Long.parseLong(companyId),
                        Long.parseLong(studentId)
                );
    }
}
