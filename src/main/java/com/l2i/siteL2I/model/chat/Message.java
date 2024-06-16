package com.l2i.siteL2I.model.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.l2i.siteL2I.model.BaseEntity;
import com.l2i.siteL2I.model.person.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Message extends BaseEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private Integer id;

    private String content;
    // private User author;

    // @Column(updatable = false, nullable = false)
    // private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    @JsonBackReference
    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
