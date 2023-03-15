package com.poli.internship.data.repository;


import com.poli.internship.data.entity.CurriculumEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurriculumRepository extends CrudRepository<CurriculumEntity, Long> {
    CurriculumEntity findById(long id);
    CurriculumEntity findByUserId(long userId);
    List<CurriculumEntity> findAll();
}
