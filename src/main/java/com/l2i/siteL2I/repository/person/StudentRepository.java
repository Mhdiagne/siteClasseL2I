package com.l2i.siteL2I.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.l2i.siteL2I.model.person.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
}
