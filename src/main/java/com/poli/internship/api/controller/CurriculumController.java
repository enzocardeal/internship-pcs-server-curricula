package com.poli.internship.api.controller;

import com.poli.internship.api.auth.GraphQLAuthorization;
import com.poli.internship.api.error.CustomError;
import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import com.poli.internship.domain.models.UserType;
import com.poli.internship.domain.usecase.*;
import graphql.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Controller;

import static com.poli.internship.domain.models.CurriculumModel.Curriculum;
import static com.poli.internship.domain.models.CurriculumModel.CurriculumInput;

import java.util.*;
import java.time.LocalDate;

@Controller
public class CurriculumController {
    @Autowired
    public CreateCurriculumUseCase createCurriculumUseCase;
    @Autowired
    public DeleteCurriculumUseCase deleteCurriculumUseCase;
    @Autowired
    public UpdateCurriculumUseCase updateCurriculumUseCase;
    @Autowired
    public GetCurriculumUseCase getCurriculumUseCase;
    @Autowired
    public GetAllCurriculaUseCase getAllCurriculaUseCase;

    @MutationMapping
    public Curriculum createCurriculum(@Argument Map input, GraphQLContext ctx){
        GraphQLAuthorization.checkAuthorization(ctx);
        if(UserType.valueOf(ctx.get("userType")) != UserType.STUDENT) {
            throw new CustomError("Wrong user type.", ErrorType.FORBIDDEN);
        }

        Map data = (Map)input.get("input");

        List<ExperienceEmbeddable> pastExperiences = new ArrayList<ExperienceEmbeddable>();
        List<ActivityEmbeddable> certificates = new ArrayList<ActivityEmbeddable>();

        ((List)data.get("pastExperiences")).forEach((experience) -> pastExperiences.add(parseExperience((Map)experience)));
        ((List)data.get("certificates")).forEach((certificate) -> certificates.add(parseActivity((Map)certificate)));

        return this.createCurriculumUseCase.exec(
                new CurriculumInput((String)ctx.get("userId"),
                (String)data.get("name"),
                (String)data.get("lastName"),
                (String)data.get("degreeCourse"),
                (LocalDate)data.get("graduationYear"),
                new HashSet<ExperienceEmbeddable>(pastExperiences),
                new HashSet<ActivityEmbeddable>(certificates)
                )
        );
    }

    @MutationMapping
    public Boolean deleteCurriculum(@Argument Map input, GraphQLContext ctx){
        GraphQLAuthorization.checkAuthorization(ctx);
        if(UserType.valueOf(ctx.get("userType")) != UserType.STUDENT) {
            throw new CustomError("Wrong user type.", ErrorType.FORBIDDEN);
        }

        Map data = (Map)input.get("input");
        return this.deleteCurriculumUseCase.exec((String)data.get("id"));
    }

    @MutationMapping
    public Curriculum updateCurriculum(@Argument Map input, GraphQLContext ctx){
        GraphQLAuthorization.checkAuthorization(ctx);
        if(UserType.valueOf(ctx.get("userType")) != UserType.STUDENT) {
            throw new CustomError("Wrong user type.", ErrorType.FORBIDDEN);
        }

        Map data = (Map)input.get("input");

        List<ExperienceEmbeddable> pastExperiences = new ArrayList<ExperienceEmbeddable>();
        List<ActivityEmbeddable> certificates = new ArrayList<ActivityEmbeddable>();

        if(data.get("pastExperiences") != null){
            ((List)data.get("pastExperiences")).forEach((experience) -> pastExperiences.add(parseExperience((Map)experience)));
        }
        if(data.get("certificates") != null){
            ((List)data.get("certificates")).forEach((certificate) -> certificates.add(parseActivity((Map)certificate)));
        }

        Curriculum positionToBeUpdated = this.getCurriculumUseCase.exec(ctx.get("userId"));

        return this.updateCurriculumUseCase.exec(
                new CurriculumInput(
                        (String)ctx.get("userId"),
                        data.get("name") != null ? (String)data.get("name") : positionToBeUpdated.name(),
                        data.get("lastName") != null ? (String)data.get("lastName") : positionToBeUpdated.lastName(),
                        data.get("degreeCourse") != null ? (String)data.get("degreeCourse") : positionToBeUpdated.degreeCourse(),
                        data.get("graduationYear") != null ? (LocalDate)data.get("graduationYear") : positionToBeUpdated.graduationYear(),
                        data.get("pastExperiences") != null ? new HashSet<ExperienceEmbeddable>(pastExperiences) : positionToBeUpdated.pastExperiences(),
                        data.get("certificates") != null ? new HashSet<ActivityEmbeddable>(certificates) : positionToBeUpdated.certificates()
                ),
                (String)data.get("id")
        );
    }

    @QueryMapping
    public Curriculum getCurriculum(GraphQLContext ctx){
        GraphQLAuthorization.checkAuthorization(ctx);
        if(UserType.valueOf(ctx.get("userType")) != UserType.STUDENT) {
            throw new CustomError("Wrong user type.", ErrorType.FORBIDDEN);
        }

        return this.getCurriculumUseCase.exec((String)ctx.get("userId"));
    }

    /*
    @QueryMapping
    public List<Curriculum> getAllCurricula(){
        return this.getAllCurriculaUseCase.exec();
    }
    */

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
