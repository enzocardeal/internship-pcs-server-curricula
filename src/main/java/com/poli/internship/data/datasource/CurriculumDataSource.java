package com.poli.internship.data.datasource;

import com.poli.internship.data.entity.CurriculumEntity;
import com.poli.internship.data.mapper.CurriculumMapper;
import com.poli.internship.data.repository.CurriculumRepository;
import com.poli.internship.domain.models.CurriculumModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.poli.internship.domain.models.CurriculumModel.CurriculumInput;
import static com.poli.internship.domain.models.CurriculumModel.Curriculum;


@Service
public class CurriculumDataSource {
    @Autowired
    public CurriculumRepository repository;

    public Curriculum createCurriculum(CurriculumInput curriculumInput)
    {
        CurriculumEntity curriculumEntity = repository.save(new CurriculumEntity(
                Long.parseLong(curriculumInput.userId()),
                curriculumInput.name(),
                curriculumInput.lastName(),
                curriculumInput.degreeCourse(),
                curriculumInput.graduationYear(),
                curriculumInput.pastExperiences(),
                curriculumInput.certificates()
        ));
        return CurriculumMapper.INSTANCE.curriculumEntityToModel(curriculumEntity);
    }

    public Curriculum getCurriculumById(String id){
        CurriculumEntity curriculumEntity = repository.findById(Long.parseLong(id));
        return CurriculumMapper.INSTANCE.curriculumEntityToModel(curriculumEntity);
    }

    public Curriculum getCurriculumByUserId(String userId){
        CurriculumEntity curriculumEntity = repository.findByUserId(Long.parseLong(userId));
        return CurriculumMapper.INSTANCE.curriculumEntityToModel(curriculumEntity);
    }

    public List<Curriculum> getAllCurricula(){
        List<CurriculumEntity> curriculumEntities = repository.findAll();
        return CurriculumMapper.INSTANCE.curriculumEntitiesToModels(curriculumEntities);
    }

    public Boolean deleteCurriculum(String id){
        if(repository.existsById(Long.parseLong(id))){
            repository.deleteById(Long.parseLong(id));
            return true;
        }
        return false;
    }

    public Curriculum updateCurriculum(CurriculumInput curriculumInput, Long id){
        CurriculumEntity curriculumEntity = repository.save(
                new CurriculumEntity(
                        id,
                        Long.parseLong(curriculumInput.userId()),
                        curriculumInput.name(),
                        curriculumInput.lastName(),
                        curriculumInput.degreeCourse(),
                        curriculumInput.graduationYear(),
                        curriculumInput.pastExperiences(),
                        curriculumInput.certificates()
                )
        );
        return CurriculumMapper.INSTANCE.curriculumEntityToModel(curriculumEntity);
    }

}
