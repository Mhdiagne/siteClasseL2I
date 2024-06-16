package com.l2i.siteL2I.repository.classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.l2i.siteL2I.model.classroom.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    
}
