package com.l2i.siteL2I.service.person;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.l2i.siteL2I.dto.person.ProfessorRequest;
import com.l2i.siteL2I.dto.person.ProfessorResponse;
import com.l2i.siteL2I.model.person.Professor;
import com.l2i.siteL2I.repository.person.ProfessorRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ResponseEntity<List<ProfessorResponse>> getAll() {
        try {
            List<Professor> items = new ArrayList<>();
            professorRepository.findAll().forEach(items::add);
    
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
            List<ProfessorResponse> responseItems = items.stream().map(this::mapToProfessorResponse).collect(Collectors.toList());
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

    public ResponseEntity<ProfessorResponse> create(ProfessorRequest requestItem) {
        try {
            ProfessorResponse saveResponseItem = mapToProfessorResponse(professorRepository.save(mapToProfessor(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<ProfessorResponse> update(Integer id, ProfessorRequest requestItem) {
        Optional<Professor> existingItemOptional = professorRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Professor existingItem = existingItemOptional.get();
            
            existingItem.setDepartment(requestItem.getDepartment());
            existingItem.setSpecialityProfessor(requestItem.getSpecialityProfessor());
            existingItem.setClasseroom(requestItem.getClasseroom());
            existingItem.setCourses(requestItem.getCourses());
            existingItem.setName(requestItem.getName());
            existingItem.setEmail(requestItem.getEmail());
            existingItem.setPassword(requestItem.getPassword());
            existingItem.setMessages(requestItem.getMessages());
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
        .classeroom(professorRequest.getClasseroom())
        .name(professorRequest.getName())
        .email(professorRequest.getEmail())
        .password(professorRequest.getPassword())
        .messages(professorRequest.getMessages())
        .courses(professorRequest.getCourses())
        .creatAt(LocalDateTime.now())
        .lastModifiedAt(LocalDateTime.now())
        .build();
    }
}
