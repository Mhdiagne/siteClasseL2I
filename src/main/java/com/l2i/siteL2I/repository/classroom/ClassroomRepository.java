package com.l2i.siteL2I.repository.classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.l2i.siteL2I.model.classroom.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    
}
