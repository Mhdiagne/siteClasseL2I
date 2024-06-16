package com.l2i.siteL2I.dto.person;

import java.time.LocalDateTime;
import java.util.List;

import com.l2i.siteL2I.model.chat.Message;
import com.l2i.siteL2I.model.classroom.Classroom;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentResponse {

    private Integer id;
    private String specialityStudent;
    private Classroom classeroom;

    private String name;
    private String email;
    private String password;
    private List<Message> messages;
    
    private LocalDateTime lastModifiedAt;
    private LocalDateTime creatAt;
    private String createdBy;
}
