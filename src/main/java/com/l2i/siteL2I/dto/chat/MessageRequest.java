package com.l2i.siteL2I.dto.chat;

import com.l2i.siteL2I.model.chat.Forum;
import com.l2i.siteL2I.model.person.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageRequest {
    
    private String content;
    private Forum forum;
    private User author;

    private String createdBy;
    private String lastModifiedBy;
}
