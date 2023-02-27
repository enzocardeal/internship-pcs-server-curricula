package com.poli.internship.data.mapper;

import com.poli.internship.data.entity.CurriculumEntity;
import com.poli.internship.domain.models.CurriculumModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurriculumMapper {
    CurriculumMapper INSTANCE = Mappers.getMapper(CurriculumMapper.class);

    CurriculumModel curriculumEntityToModel(CurriculumEntity entity);
}
