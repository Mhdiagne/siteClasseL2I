package com.l2i.siteL2I.service.classroom;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.l2i.siteL2I.dto.classroom.ClassroomRequest;
import com.l2i.siteL2I.dto.classroom.ClassroomResponse;
import com.l2i.siteL2I.model.classroom.Classroom;
import com.l2i.siteL2I.repository.classroom.ClassroomRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ResponseEntity<List<ClassroomResponse>> getAll() {
        try {
            List<Classroom> items = new ArrayList<>();
            classroomRepository.findAll().forEach(items::add);
    
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
            List<ClassroomResponse> responseItems = items.stream().map(this::mapToClassroomResponse).collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ClassroomResponse> getById(Integer id) {
        Optional<Classroom> existingItemOptional = classroomRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToClassroomResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Classroom getByIdClassroom(Integer id) {
        Optional<Classroom> existingItemOptional = classroomRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return existingItemOptional.get();
        } else {
            return null;
        }
    }

    public ResponseEntity<ClassroomResponse> create(ClassroomRequest requestItem) {
        try {
            ClassroomResponse saveResponseItem = mapToClassroomResponse(classroomRepository.save(mapToClassroom(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<ClassroomResponse> update(Integer id, ClassroomRequest requestItem) {
        Optional<Classroom> existingItemOptional = classroomRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Classroom existingItem = existingItemOptional.get();
            
            existingItem.setName(requestItem.getName());
            // existingItem.setStudents(requestItem.getStudents());
            // existingItem.setProfessors(requestItem.getProfessors());
            // existingItem.setCourses(requestItem.getCourses());
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToClassroomResponse(classroomRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            classroomRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private ClassroomResponse mapToClassroomResponse(Classroom classroom) {
        return ClassroomResponse.builder()
        .id(classroom.getId())
        .name(classroom.getName())
        .promotionNumber(classroom.getPromotionNumber())
        .students(classroom.getStudents())
        .professors(classroom.getProfessors())
        .courses(classroom.getCourses())
        .creatAt(classroom.getCreatAt())
        .lastModifiedAt(classroom.getLastModifiedAt())
        .createdBy(classroom.getCreatedBy())
        .build();
    }

    private Classroom mapToClassroom(ClassroomRequest classroomRequest) {
        return Classroom.builder()
        .name(classroomRequest.getName())
        // .students(classroomRequest.getStudents())
        // .professors(classroomRequest.getProfessors())
        // .courses(classroomRequest.getCourses())
        .creatAt(LocalDateTime.now())
        .lastModifiedAt(LocalDateTime.now())
        .build();
    }
}

