package com.l2i.siteL2I.controller.classroom;

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

import com.l2i.siteL2I.dto.classroom.ClassroomRequest;
import com.l2i.siteL2I.dto.classroom.ClassroomResponse;
import com.l2i.siteL2I.service.classroom.ClassroomService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public ResponseEntity<List<ClassroomResponse>> getAll() {
        return classroomService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ClassroomResponse> getById(@PathVariable("id") Integer id) {
        return classroomService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ClassroomResponse> create(@RequestBody ClassroomRequest requestItem) {
        return classroomService.create(requestItem);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClassroomResponse> update(@PathVariable("id") Integer id, @RequestBody ClassroomRequest requestItem) {
        return classroomService.update(id, requestItem);
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return classroomService.delete(id);
    }
}


