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

import com.l2i.siteL2I.dto.classroom.CourseRequest;
import com.l2i.siteL2I.dto.classroom.CourseResponse;
import com.l2i.siteL2I.service.classroom.CourseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        return courseService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable("id") Integer id) {
        return courseService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@RequestBody CourseRequest requestItem) {
        return courseService.create(requestItem);
    }

    @PutMapping("{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable("id") Integer id, @RequestBody CourseRequest requestItem) {
        return courseService.update(id, requestItem);
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return courseService.delete(id);
    }
}



