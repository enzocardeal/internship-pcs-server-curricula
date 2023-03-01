package com.poli.internship.data.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class ActivityEmbeddable {
    private String name;
    private String description;
    private LocalDate completedAt;
    @Column(nullable = true)
    private LocalDate expiresAt;

    protected ActivityEmbeddable(){}

    public ActivityEmbeddable(String name, String description, LocalDate completedAt, LocalDate expiresAt) {
        this.name = name;
        this.description = description;
        this.completedAt = completedAt;
        this.expiresAt = expiresAt;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public LocalDate getCompletedAt() {
        return completedAt;
    }


    public LocalDate getExpiresAt() {
        return expiresAt;
    }
}
