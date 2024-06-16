package com.l2i.siteL2I.service.person;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.l2i.siteL2I.dto.person.StudentResponse;
import com.l2i.siteL2I.dto.person.StudentRequest;
import com.l2i.siteL2I.model.person.Student;
import com.l2i.siteL2I.repository.person.StudentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseEntity<List<StudentResponse>> getAll() {
        try {
            List<Student> items = new ArrayList<>();
            studentRepository.findAll().forEach(items::add);
    
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
            List<StudentResponse> responseItems = items.stream().map(this::mapToStudentResponse).collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<StudentResponse> getById(Integer id) {
        Optional<Student> existingItemOptional = studentRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToStudentResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StudentResponse> create(StudentRequest requestItem) {
        try {
            StudentResponse saveResponseItem = mapToStudentResponse(studentRepository.save(mapToStudent(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<StudentResponse> update(Integer id, StudentRequest requestItem) {
        Optional<Student> existingItemOptional = studentRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Student existingItem = existingItemOptional.get();
            
            existingItem.setSpecialityStudent(requestItem.getSpecialityStudent());
            existingItem.setClasseroom(requestItem.getClasseroom());
            existingItem.setName(requestItem.getName());
            existingItem.setEmail(requestItem.getEmail());
            existingItem.setPassword(requestItem.getPassword());
            existingItem.setMessages(requestItem.getMessages());
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToStudentResponse(studentRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
        .id(student.getId())
        .specialityStudent(student.getSpecialityStudent())
        .classeroom(student.getClasseroom())
        .name(student.getName())
        .email(student.getEmail())
        .password(student.getPassword())
        .messages(student.getMessages())
        .lastModifiedAt(student.getLastModifiedAt())
        .creatAt(student.getCreatAt())
        .createdBy(student.getCreatedBy())
        .build();
    }

    private Student mapToStudent(StudentRequest studentRequest) {
        return Student.builder()
        .specialityStudent(studentRequest.getSpecialityStudent())
        .classeroom(studentRequest.getClasseroom())
        .name(studentRequest.getName())
        .email(studentRequest.getEmail())
        .password(studentRequest.getPassword())
        .messages(studentRequest.getMessages())
        .creatAt(LocalDateTime.now())
        .lastModifiedAt(LocalDateTime.now())
        .build();
    }
}

