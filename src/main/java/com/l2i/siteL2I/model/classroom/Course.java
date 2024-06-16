package com.l2i.siteL2I.model.classroom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.l2i.siteL2I.model.BaseEntity;
import com.l2i.siteL2I.model.person.Professor;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Course extends BaseEntity {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private Integer id;

    private String title;
    private String pdfContent;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    @JsonBackReference
    private Classroom classe;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonBackReference
    private Professor professor;
}
