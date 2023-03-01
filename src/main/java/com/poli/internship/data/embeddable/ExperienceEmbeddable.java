package com.poli.internship.data.embeddable;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class ExperienceEmbeddable {
    private String company;
    private String role;
    private String description;
    private LocalDate startedAt;
    private LocalDate endedAt;

    protected ExperienceEmbeddable(){}
    public ExperienceEmbeddable(String company, String role, String description, LocalDate startedAt, LocalDate endedAt) {
        this.company = company;
        this.role = role;
        this.description = description;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public String getCompany() {
        return company;
    }
    public String getRole() {
        return role;
    }
    public String getDescription() {return description;}
    public LocalDate getStartedAt() {
        return startedAt;
    }
    public LocalDate getEndedAt() {
        return endedAt;
    }

}
