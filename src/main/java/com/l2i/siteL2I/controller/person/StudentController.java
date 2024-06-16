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

import com.l2i.siteL2I.dto.person.StudentRequest;
import com.l2i.siteL2I.dto.person.StudentResponse;
import com.l2i.siteL2I.service.person.StudentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        return studentService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable("id") Integer id) {
        return studentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody StudentRequest requestItem) {
        return studentService.create(requestItem);
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable("id") Integer id, @RequestBody StudentRequest requestItem) {
        return studentService.update(id, requestItem);
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return studentService.delete(id);
    }
}





