package com.l2i.siteL2I.model.chat;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.l2i.siteL2I.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Forum extends BaseEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private Integer id;

    private String name;

    @OneToMany(mappedBy = "forum")
    @JsonManagedReference
    private List<Message> messages;
}
