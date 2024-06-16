package com.l2i.siteL2I.dto.classroom;

import java.time.LocalDateTime;
import java.util.List;

import com.l2i.siteL2I.model.classroom.Course;
import com.l2i.siteL2I.model.person.Professor;
import com.l2i.siteL2I.model.person.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClassroomResponse {
    
    private Integer id;
    private String name;
    private int promotionNumber;
    private List<Student> students;
    private List<Professor> professors;
    private List<Course> courses;

    private LocalDateTime lastModifiedAt;
    private LocalDateTime creatAt;
    private String createdBy;

}
