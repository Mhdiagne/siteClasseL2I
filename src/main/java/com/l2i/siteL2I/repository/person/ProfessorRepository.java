package com.l2i.siteL2I.repository.person;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.l2i.siteL2I.model.person.Professor;
import java.util.List;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    Optional<Professor> findByEmail(String email);
    
}
