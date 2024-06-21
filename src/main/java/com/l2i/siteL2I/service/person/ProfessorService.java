package com.l2i.siteL2I.service.person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.l2i.siteL2I.dto.person.ProfessorRequest;
import com.l2i.siteL2I.dto.person.ProfessorResponse;
import com.l2i.siteL2I.model.person.Professor;
import com.l2i.siteL2I.repository.person.ProfessorRepository;
import com.l2i.siteL2I.repository.person.UserRepository;
import com.l2i.siteL2I.service.classroom.ClassroomService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;

    private final ClassroomService classroomService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // injection d'un encodeur de mot
    private final Logger logger = LoggerFactory.getLogger(ProfessorService.class);
    // de

    // public ResponseEntity<ProfessorResponse> create(ProfessorRequest requestItem)
    // {
    // if (professorRepository.findByEmail(requestItem.getEmail()).isPresent()) {
    // System.out.println("Début de la création du professeur avec la demande : {}",
    // request);

    // // L'utilisateur avec cette adresse e-mail existe déjà, renvoyez une erreur.
    // throw new RuntimeException("L'utilisateur avec cette adresse e-mail existe
    // déjà.");
    // }

    // try {
    // /* Encodons le password pour plus de securite */
    // requestItem.setPassword(passwordEncoder.encode(requestItem.getPassword()));
    // ProfessorResponse saveResponseItem = mapToProfessorResponse(
    // professorRepository.save(mapToProfessor(requestItem)));
    // return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    // }
    // }

    public ResponseEntity<ProfessorResponse> create(ProfessorRequest requestItem) {
        logger.info("Début de la création du professeur avec la demande : {}", requestItem);

        // Vérifiez si un professeur avec l'e-mail donné existe déjà
        if (professorRepository.findByEmail(requestItem.getEmail()).isPresent()) {
            logger.warn("Un professeur avec l'e-mail {} existe déjà.", requestItem.getEmail());
            throw new RuntimeException("L'utilisateur avec cette adresse e-mail existe déjà.");
        }

        try {
            // Encoder le mot de passe pour la sécurité
            requestItem.setPassword(passwordEncoder.encode(requestItem.getPassword()));

            // Mapper la requête vers l'entité Professor et sauvegarder
            Professor professor = mapToProfessor(requestItem);
            ProfessorResponse saveResponseItem = mapToProfessorResponse(professorRepository.save(professor));

            logger.info("Professeur créé avec succès : {}", saveResponseItem);
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur de violation de l'intégrité des données : ", e);
            throw new RuntimeException("Violation d'intégrité des données", e);
        } catch (Exception e) {
            logger.error("Erreur lors de la création du professeur : ", e);
            throw new RuntimeException("Erreur inconnue lors de la création du professeur", e);
        }
    }

    public ResponseEntity<List<ProfessorResponse>> getAll() {
        try {
            List<Professor> items = new ArrayList<>();
            professorRepository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<ProfessorResponse> responseItems = items.stream().map(this::mapToProfessorResponse)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProfessorResponse> getById(Integer id) {
        Optional<Professor> existingItemOptional = professorRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToProfessorResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Professor getByIdProfessor(Integer id) {
        Optional<Professor> existingItemOptional = professorRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return existingItemOptional.get();
        } else {
            return null;
        }
    }

    public ResponseEntity<ProfessorResponse> update(Integer id, ProfessorRequest requestItem) {
        Optional<Professor> existingItemOptional = professorRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Professor existingItem = existingItemOptional.get();

            existingItem.setDepartment(requestItem.getDepartment());
            existingItem.setSpecialityProfessor(requestItem.getSpecialityProfessor());
            existingItem.setClasseroom(classroomService.getByIdClassroom(requestItem.getClasseroom_id()));
            existingItem.setCourses(requestItem.getCourses());
            existingItem.setName(requestItem.getName());
            existingItem.setEmail(requestItem.getEmail());
            existingItem.setPassword(requestItem.getPassword());
            // existingItem.setMessages(requestItem.getMessages());
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToProfessorResponse(professorRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            professorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private ProfessorResponse mapToProfessorResponse(Professor professor) {
        return ProfessorResponse.builder()
                .id(professor.getId())
                .department(professor.getDepartment())
                .specialityProfessor(professor.getSpecialityProfessor())
                .classeroom(professor.getClasseroom())
                .courses(professor.getCourses())
                .name(professor.getName())
                .email(professor.getEmail())
                .password(professor.getPassword())
                .messages(professor.getMessages())
                .lastModifiedAt(professor.getLastModifiedAt())
                .creatAt(professor.getCreatAt())
                .createdBy(professor.getCreatedBy())
                .build();
    }

    private Professor mapToProfessor(ProfessorRequest professorRequest) {
        return Professor.builder()
                .department(professorRequest.getDepartment())
                .specialityProfessor(professorRequest.getSpecialityProfessor())
                // .classeroom(classroomService.getByIdClassroom(professorRequest.getClasseroom_id()))
                .name(professorRequest.getName())
                .email(professorRequest.getEmail())
                .password(professorRequest.getPassword())
                // .messages(professorRequest.getMessages())
                .courses(professorRequest.getCourses())
                .creatAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

    public Optional<Professor> getProfByMail(String email) {
        return professorRepository.findByEmail(email);
    }
}
