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
    private String type;
    private List<Message> messages;

    private String createdBy;

    public StudentRequest(String specialityStudent, Integer classeroom_id, String name, String email, String password,
         List<Message> messages, String createdBy) {
        this.specialityStudent = specialityStudent;
        this.classeroom_id = classeroom_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = "student";
        this.messages = messages;
        this.createdBy = createdBy;
    }
    
}
