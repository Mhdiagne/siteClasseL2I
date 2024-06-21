package com.l2i.siteL2I.dto.person;

import java.io.Serializable;
import java.util.List;

import com.l2i.siteL2I.model.classroom.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String department;
    private String specialityProfessor;
    private Integer classeroom_id;
    private List<Course> courses;

    private String name;
    private String email;
    private String password;
    private String type;
    // private List<Message> messages;

    private String createdBy;

    public ProfessorRequest(String department, String specialityProfessor, Integer classeroom_id, List<Course> courses,
            String name, String email, String password, String createdBy) {
        this.department = department;
        this.specialityProfessor = specialityProfessor;
        this.classeroom_id = classeroom_id;
        this.courses = courses;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = "professor";
        // this.messages = messages;
        this.createdBy = createdBy;
    }
}
