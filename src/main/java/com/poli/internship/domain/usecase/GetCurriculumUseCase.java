package com.poli.internship.domain.usecase;

import com.poli.internship.data.datasource.CurriculumDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;

@Service
public class GetCurriculumUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public Curriculum exec(String userId){
        return this.dataSource.getCurriculumByUserId(userId);
    }
}
