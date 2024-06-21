package com.l2i.siteL2I.model.person;

import com.l2i.siteL2I.model.classroom.Classroom;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@DiscriminatorValue("student")
public class Student extends User {
    @Builder.Default
    private String role = "student";
    private String specialityStudent;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    // @JsonBackReference("student_classroom")
    private Classroom classeroom;
}
