package com.l2i.siteL2I.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageRequest {
    
    private String content;
    private Integer forum_id;
    private Integer author_id;

    private String createdBy;
    private String lastModifiedBy;
}
