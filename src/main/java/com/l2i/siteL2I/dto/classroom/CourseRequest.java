package com.l2i.siteL2I.dto.classroom;

import com.l2i.siteL2I.model.classroom.Classroom;
import com.l2i.siteL2I.model.person.Professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest {
    
    private String title;
    private String pdfContent;
    private Classroom classeroom;
    private Professor professor;

    private String createdBy;
}
