package com.l2i.siteL2I.controller.person;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.l2i.siteL2I.dto.person.ProfessorRequest;
import com.l2i.siteL2I.dto.person.ProfessorResponse;
import com.l2i.siteL2I.service.person.ProfessorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> getAll() {
        return professorService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfessorResponse> getById(@PathVariable("id") Integer id) {
        return professorService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ProfessorResponse> create(@RequestBody ProfessorRequest requestItem) {
        return professorService.create(requestItem);
    }

    // @PostMapping("/test")
    // public ResponseEntity<?> createUser(@RequestBody User requestItem) {
    // return professorService.createUser(requestItem);
    // }

    @PutMapping("{id}")
    public ResponseEntity<ProfessorResponse> update(@PathVariable("id") Integer id,
            @RequestBody ProfessorRequest requestItem) {
        return professorService.update(id, requestItem);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return professorService.delete(id);
    }
}
