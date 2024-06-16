package com.l2i.siteL2I.model.classroom;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.l2i.siteL2I.model.BaseEntity;
import com.l2i.siteL2I.model.person.Professor;
import com.l2i.siteL2I.model.person.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Classroom extends BaseEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private Integer id;

    private String name;
    private int promotionNumber;

    @OneToMany(mappedBy = "classe")
    @JsonManagedReference
    private List<Student> students;

    @OneToMany(mappedBy = "classe")
    @JsonManagedReference
    private List<Professor> professors;

    @OneToMany(mappedBy = "classe")
    @JsonManagedReference
    private List<Course> courses;

}
