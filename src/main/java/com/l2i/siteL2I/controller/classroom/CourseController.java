package com.l2i.siteL2I.controller.classroom;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<CourseResponse> create(
            @RequestParam("course") String course,
            @RequestParam("pdf") MultipartFile pdf) throws JsonMappingException,
            JsonProcessingException {
        // Create and configure the ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        CourseRequest requestItem = objectMapper.readValue(course,
                CourseRequest.class);
        return courseService.create(requestItem, pdf);
    }

    // Télécharger le cours en PDF
    @GetMapping(path = "/{id}/pdf")
    public ResponseEntity<byte[]> trouverArticlePdf(@PathVariable Integer id) throws IOException {
        // Téléchargez le document PDF en tant que tableau d'octets
        byte[] pdf = courseService.downloadDocument(id);

        // Retourne la réponse avec le PDF et le type de contenu correct
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF) // Utilisez le type de contenu correct pour le PDF
                .body(pdf);
    }

    @PutMapping("{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable("id") Integer id,
            @RequestBody CourseRequest requestItem) {

        // // Create and configure the ObjectMapper
        // ObjectMapper objectMapper = new ObjectMapper();
        // Course article = objectMapper.readValue(articleString, Article.class);

        return courseService.update(id, requestItem);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return courseService.delete(id);
    }
}
