package com.poli.internship.data.mapper;

import com.poli.internship.data.entity.CurriculumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;

@Mapper
public interface CurriculumMapper {
    CurriculumMapper INSTANCE = Mappers.getMapper(CurriculumMapper.class);

    Curriculum curriculumEntityToModel(CurriculumEntity entity);
    List<Curriculum> curriculumEntitiesToModels(List<CurriculumEntity> entities);
}
