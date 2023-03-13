package com.poli.internship.domain.usecase;

import com.poli.internship.data.datasource.CurriculumDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public Boolean exec(String id) {
        return this.dataSource.deleteCurriculum(id);
    }
}
