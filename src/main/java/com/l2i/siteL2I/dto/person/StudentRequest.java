package com.l2i.siteL2I.dto.person;

import java.util.List;

import com.l2i.siteL2I.model.chat.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentRequest {
    
    private String specialityStudent;
    private Integer classeroom_id;

    private String name;
    private String email;
    private String password;
    private List<Message> messages;

    private String createdBy;
}
