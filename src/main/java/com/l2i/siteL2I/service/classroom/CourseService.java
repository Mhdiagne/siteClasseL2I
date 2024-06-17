package com.l2i.siteL2I.service.classroom;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.l2i.siteL2I.dto.classroom.CourseRequest;
import com.l2i.siteL2I.dto.classroom.CourseResponse;
import com.l2i.siteL2I.model.classroom.Course;
import com.l2i.siteL2I.repository.classroom.CourseRepository;
import com.l2i.siteL2I.service.person.ProfessorService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final ProfessorService professorService;
    private final ClassroomService classroomService;

    public ResponseEntity<List<CourseResponse>> getAll() {
        try {
            List<Course> items = new ArrayList<>();
            courseRepository.findAll().forEach(items::add);
    
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
            List<CourseResponse> responseItems = items.stream().map(this::mapToCourseResponse).collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CourseResponse> getById(Integer id) {
        Optional<Course> existingItemOptional = courseRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToCourseResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<CourseResponse> create(CourseRequest requestItem) {
        try {
            CourseResponse saveResponseItem = mapToCourseResponse(courseRepository.save(mapToCourse(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<CourseResponse> update(Integer id, CourseRequest requestItem) {
        Optional<Course> existingItemOptional = courseRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Course existingItem = existingItemOptional.get();
            
            existingItem.setTitle(requestItem.getTitle());
            existingItem.setPdfContent(requestItem.getPdfContent());
            existingItem.setClasseroom(classroomService.getByIdClassroom(requestItem.getClasseroom_id()));
            existingItem.setProfessor(professorService.getByIdProfessor(requestItem.getProfessor_id()));
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToCourseResponse(courseRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            courseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.builder()
        .id(course.getId())
        .title(course.getTitle())
        .pdfContent(course.getPdfContent())
        .classeroom(course.getClasseroom())
        .professor(course.getProfessor())
        .creatAt(course.getCreatAt())
        .lastModifiedAt(course.getLastModifiedAt())
        .createdBy(course.getCreatedBy())
        .build();
    }

    private Course mapToCourse(CourseRequest courseRequest) {
        return Course.builder()
        .title(courseRequest.getTitle())
        .pdfContent(courseRequest.getPdfContent())
        .classeroom(classroomService.getByIdClassroom(courseRequest.getClasseroom_id()))
        .professor(professorService.getByIdProfessor(courseRequest.getProfessor_id()))
        .creatAt(LocalDateTime.now())
        .lastModifiedAt(LocalDateTime.now())
        .build();
    }
}