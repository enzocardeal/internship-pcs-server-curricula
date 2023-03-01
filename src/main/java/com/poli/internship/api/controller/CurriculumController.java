package com.poli.internship.api.controller;

import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.domain.models.CurriculumModel;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import com.poli.internship.domain.usecase.CreateCurriculumUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.time.LocalDate;

@Controller
public class CurriculumController {
    @Autowired
    public CreateCurriculumUseCase createCurriculumUseCase;

    @MutationMapping
    public CurriculumModel createCurriculum(@Argument Map input){
        Map data = (Map)input.get("input");

        List<ExperienceEmbeddable> pastExperiences = new ArrayList<ExperienceEmbeddable>();
        List<ActivityEmbeddable> certificates = new ArrayList<ActivityEmbeddable>();

        ((List)data.get("pastExperiences")).forEach((experience) -> pastExperiences.add(parseExperience((Map)experience)));
        ((List)data.get("certificates")).forEach((certificate) -> certificates.add(parseActivity((Map)certificate)));

        return this.createCurriculumUseCase.exec(
                (String)data.get("name"),
                (String)data.get("lastName"),
                (String)data.get("degreeCourse"),
                (LocalDate)data.get("graduationYear"),
                new HashSet<ExperienceEmbeddable>(pastExperiences),
                new HashSet<ActivityEmbeddable>(certificates)
        );
    }

    private ExperienceEmbeddable parseExperience(Map data){
        return new ExperienceEmbeddable(
                (String)data.get("company"),
                (String)data.get("role"),
                (String)data.get("description"),
                (LocalDate)data.get("startedAt"),
                (LocalDate)data.get("endedAt")
        );
    }

    private ActivityEmbeddable parseActivity(Map data){
        return new ActivityEmbeddable(
                (String)data.get("name"),
                (String)data.get("description"),
                (LocalDate)data.get("completedAt"),
                (LocalDate)data.get("expiresAt")
        );
    }
}
