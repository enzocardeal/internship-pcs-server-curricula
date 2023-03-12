package com.poli.internship.domain.usecase;

import com.poli.internship.data.datasource.CurriculumDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;

@Service
public class GetAllCurriculaUseCase {
    @Autowired
    private CurriculumDataSource dataSource;

    public List<Curriculum> exec(){
        return this.dataSource.getAllCurricula();
    }
}
