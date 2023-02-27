package com.poli.internship.data.entity;

import com.poli.internship.data.embeddable.ActivityEmbeddable;
import com.poli.internship.data.embeddable.ExperienceEmbeddable;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="curriculum")
public class CurriculumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private String degreeCourse;
    private LocalDate graduationYear;
    @ElementCollection
    @CollectionTable(name="experiences", joinColumns = @JoinColumn(name = "user_id"))
    private Set<ExperienceEmbeddable> pastExperiences;
    @ElementCollection
    @CollectionTable(name="certificates", joinColumns = @JoinColumn(name = "user_id"))
    private Set<ActivityEmbeddable> certificates;

    protected CurriculumEntity(){

    }

    public CurriculumEntity(String name,
                            String lastName,
                            String degreeCourse,
                            LocalDate graduationYear,
                            Set<ExperienceEmbeddable> pastExperiences,
                            Set<ActivityEmbeddable> certificates) {
        this.name = name;
        this.lastName = lastName;
        this.degreeCourse = degreeCourse;
        this.graduationYear = graduationYear;
        this.pastExperiences = pastExperiences;
        this.certificates = certificates;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDegreeCourse() {
        return degreeCourse;
    }

    public LocalDate getGraduationYear() {
        return graduationYear;
    }

    public Set<ExperienceEmbeddable> getPastExperiences() {
        return pastExperiences;
    }

    public Set<ActivityEmbeddable> getCertificates() {
        return certificates;
    }

}
