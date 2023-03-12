package com.poli.internship.domain.models;

import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;

import java.time.LocalDate;
import java.util.Set;

public class CurriculumModel {
    public static record Curriculum(
            String id,
            String name,
            String lastName,
            String degreeCourse,
            LocalDate graduationYear,
            Set<ExperienceEmbeddable> pastExperiences,
            Set<ActivityEmbeddable> certificates
    ){};
    public static record CurriculumInput(
            String name,
            String lastName,
            String degreeCourse,
            LocalDate graduationYear,
            Set<ExperienceEmbeddable> pastExperiences,
            Set<ActivityEmbeddable> certificates
    ){};
}
