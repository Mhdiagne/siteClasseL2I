package com.l2i.siteL2I.dto.person;

import java.util.List;

import com.l2i.siteL2I.model.chat.Message;
import com.l2i.siteL2I.model.classroom.Classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentRequest {
    
    private String specialityStudent;
    private Classroom classeroom;

    private String name;
    private String email;
    private String password;
    private List<Message> messages;

    private String createdBy;
}
