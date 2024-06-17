package com.l2i.siteL2I.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest {
    
    private String title;
    private String pdfContent;
    private Integer classeroom_id;
    private Integer professor_id;

    private String createdBy;
}
