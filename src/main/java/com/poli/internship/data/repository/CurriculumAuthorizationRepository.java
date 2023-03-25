package com.poli.internship.data.repository;

import com.poli.internship.data.entity.CurriculumAuthorizationEntity;
import org.springframework.data.repository.CrudRepository;

public interface CurriculumAuthorizationRepository extends CrudRepository<CurriculumAuthorizationEntity, Long> {
    CurriculumAuthorizationEntity findByCompanyIdAndStudentId(long companyId, long studentId);
    boolean existsByCompanyIdAndStudentId(long companyId, long studentId);
}
