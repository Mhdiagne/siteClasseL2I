package com.l2i.siteL2I.service.classroom;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.l2i.siteL2I.dto.classroom.CourseRequest;
import com.l2i.siteL2I.dto.classroom.CourseResponse;
import com.l2i.siteL2I.model.classroom.Course;
import com.l2i.siteL2I.repository.classroom.CourseRepository;
import com.l2i.siteL2I.service.FileUploadUtil;
import com.l2i.siteL2I.service.person.ProfessorService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {
    @Value("${dossier.pj}")
    private String dossierImg;
    private final CourseRepository courseRepository;

    private final ProfessorService professorService;
    private final ClassroomService classroomService;

    public ResponseEntity<List<CourseResponse>> getAll() {
        try {
            List<Course> items = new ArrayList<>();
            courseRepository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<CourseResponse> responseItems = items.stream().map(this::mapToCourseResponse)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CourseResponse> getById(Integer id) {
        Optional<Course> existingItemOptional = courseRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToCourseResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<CourseResponse> create(CourseRequest requestItem,
            MultipartFile pdf) {
        try {
            Course course = courseRepository.save(mapToCourse(requestItem));

            /* Zone a risque */
            if (pdf != null) {

                if (FileUploadUtil.isPdfOrDoc(pdf)) {
                    String fileName = pdf.getOriginalFilename() + "_" + course.getId();
                    FileUploadUtil.saveFile(dossierImg, fileName, pdf);
                    course.setPdfContent(fileName);
                    return ResponseEntity.ok(mapToCourseResponse(courseRepository.save(course)));
                } else {
                    throw new IOException("Un document pdf est requise comme ressource ! " +
                            "Cependant le cours est bien cree sans pdf. " + "Vous pouvez lui ajouter un document avec" +
                            "l'url : /api/course/" + course.getId() + "/pdf ");

                }
            }

            /* */

            CourseResponse saveResponseItem = mapToCourseResponse(course);
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
       // Telecharger la couverture d'une Image
    public byte[] downloadDocument(Integer id) throws IOException {
        String imageName = courseRepository.findById(id).get().getPdfContent();
        if (imageName != null) {
            byte[] image = FileUploadUtil.getFile(dossierImg, imageName);
            return image;
        } else {
            throw new EntityNotFoundException("Cette article n'a pas d'image de couverture");
        }
    }

    public ResponseEntity<CourseResponse> update(Integer id, CourseRequest requestItem) {
        Optional<Course> existingItemOptional = courseRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Course existingItem = existingItemOptional.get();

            existingItem.setTitle(requestItem.getTitle());
            existingItem.setPdfContent(requestItem.getPdfContent());
            existingItem.setClasseroom(classroomService.getByIdClassroom(requestItem.getClasseroom_id()));
            existingItem.setProfessor(professorService.getByIdProfessor(requestItem.getProfessor_id()));
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToCourseResponse(courseRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            courseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .pdfContent(course.getPdfContent())
                .classeroom(course.getClasseroom())
                .professor(course.getProfessor())
                .creatAt(course.getCreatAt())
                .lastModifiedAt(course.getLastModifiedAt())
                .createdBy(course.getCreatedBy())
                .build();
    }

    private Course mapToCourse(CourseRequest courseRequest) {
        return Course.builder()
                .title(courseRequest.getTitle())
                .pdfContent(courseRequest.getPdfContent())
                .classeroom(classroomService.getByIdClassroom(courseRequest.getClasseroom_id()))
                .professor(professorService.getByIdProfessor(courseRequest.getProfessor_id()))
                .creatAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}