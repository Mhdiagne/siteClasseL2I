package com.l2i.siteL2I.dto.classroom;

import java.time.LocalDateTime;

import com.l2i.siteL2I.model.classroom.Classroom;
import com.l2i.siteL2I.model.person.Professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseResponse {

    private Integer id;
    private String title;
    private String pdfContent;
    private Classroom classeroom;
    private Professor professor;

    private LocalDateTime lastModifiedAt;
    private LocalDateTime creatAt;
    private String createdBy;

}
