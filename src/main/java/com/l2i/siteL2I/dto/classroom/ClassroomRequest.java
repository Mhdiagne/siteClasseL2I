package com.l2i.siteL2I.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassroomRequest {
    
    private String name;
    // private int promotionNumber;
    // private List<Student> students;
    // private List<Professor> professors;
    // private List<Course> courses;

    private String createdBy;
}
