package com.l2i.siteL2I.dto.chat;

import java.time.LocalDateTime;
import java.util.List;

import com.l2i.siteL2I.model.chat.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ForumResponse {
    
    private Integer id;
    private String name;
    private List<Message> messages;
    
    private LocalDateTime lastModifiedAt;
    private LocalDateTime creatAt;
    private String createdBy;

}
