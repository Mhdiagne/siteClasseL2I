package com.l2i.siteL2I.service.person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.l2i.siteL2I.dto.person.StudentRequest;
import com.l2i.siteL2I.dto.person.StudentResponse;
import com.l2i.siteL2I.model.person.Student;
import com.l2i.siteL2I.repository.person.StudentRepository;
import com.l2i.siteL2I.service.classroom.ClassroomService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final ClassroomService classroomService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // injection d'un encodeur de
                                                                                       // motDePasse
    private final Logger logger = LoggerFactory.getLogger(ProfessorService.class);

    public ResponseEntity<List<StudentResponse>> getAll() {
        try {
            List<Student> items = new ArrayList<>();
            studentRepository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<StudentResponse> responseItems = items.stream().map(this::mapToStudentResponse)
                    .collect(Collectors.toList());
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

    public Student getByIdStudent(Integer id) {
        Optional<Student> existingItemOptional = studentRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return existingItemOptional.get();
        } else {
            return null;
        }
    }

    public ResponseEntity<StudentResponse> create(StudentRequest requestItem) {
        if (studentRepository.findByEmail(requestItem.getEmail()).isPresent()) {
            // L'utilisateur avec cette adresse e-mail existe déjà, renvoyez une erreur.
            logger.warn("Un etudiant avec l'e-mail {} existe déjà.", requestItem.getEmail());
            throw new RuntimeException("L'utilisateur avec cette adresse e-mail existe déjà.");
        }

        try {
            /* Encodons le password pour plus de securite */
            requestItem.setPassword(passwordEncoder.encode(requestItem.getPassword()));
            StudentResponse saveResponseItem = mapToStudentResponse(studentRepository.save(mapToStudent(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur lors de la création du professeur : ", e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<StudentResponse> update(Integer id, StudentRequest requestItem) {
        Optional<Student> existingItemOptional = studentRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Student existingItem = existingItemOptional.get();

            existingItem.setSpecialityStudent(requestItem.getSpecialityStudent());
            existingItem.setClasseroom(classroomService.getByIdClassroom(requestItem.getClasseroom_id()));
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
                // .classeroom(classroomService.getByIdClassroom(studentRequest.getClasseroom_id()))
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .password(studentRequest.getPassword())
                .messages(studentRequest.getMessages())
                .creatAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

    public Student getStudentByMail(String email) {
        return studentRepository.findByEmail(email).get();
    }
}
