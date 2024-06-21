package com.l2i.siteL2I.model.person;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.l2i.siteL2I.model.chat.Message;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Professor.class, name = "professor"),
        @JsonSubTypes.Type(value = Student.class, name = "student")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(nullable = false, updatable = false)
    private Integer id;

    private String name;
    private String firstName;

    @Column(unique = true)
    private String email;
    // @JsonIgnore
    private String password;
    // @Column(nullable = false)
    private String role;
    private String photo;

    private LocalDateTime creatAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

    @OneToMany(mappedBy = "user")
    // @JsonManagedReference("userMessage")
    private List<Message> messages;

    public User(String mail, String password, String tof, String role) {
        super();
        this.email = mail;
        this.password = password;
        this.photo = tof;
        this.role = role;

    }

}
