package com.l2i.siteL2I.model.person;

import java.util.List;

import com.l2i.siteL2I.model.classroom.Classroom;
import com.l2i.siteL2I.model.classroom.Course;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@DiscriminatorValue("professor")
public class Professor extends User {
    @Builder.Default
    private String role = "professor";
    private String department;
    private String specialityProfessor;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    // @JsonBackReference("professor_classroom")
    private Classroom classeroom;

    @OneToMany(mappedBy = "professor")
    // @JsonManagedReference("professor-classroom")
    private List<Course> courses;

}
