package com.poli.internship.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name="curriculum_authorization")
public class CurriculumAuthorizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long companyId;
    private Long studentId;

    protected CurriculumAuthorizationEntity() {}
    public CurriculumAuthorizationEntity(Long companyId, Long studentId) {
        this.companyId = companyId;
        this.studentId = studentId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
