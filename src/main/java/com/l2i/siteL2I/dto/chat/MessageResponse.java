package com.l2i.siteL2I.dto.chat;

import java.time.LocalDateTime;

import com.l2i.siteL2I.model.chat.Forum;
import com.l2i.siteL2I.model.person.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageResponse {
    
    private Integer id;
    private String content;
    private Forum forum;
    private User author;

    private LocalDateTime creatAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
}
