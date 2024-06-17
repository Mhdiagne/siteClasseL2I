package com.l2i.siteL2I;

import java.util.List;
// import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.l2i.siteL2I.dto.chat.ForumRequest;
import com.l2i.siteL2I.dto.chat.MessageRequest;
import com.l2i.siteL2I.dto.classroom.ClassroomRequest;
import com.l2i.siteL2I.dto.classroom.CourseRequest;
import com.l2i.siteL2I.dto.person.ProfessorRequest;
import com.l2i.siteL2I.dto.person.StudentRequest;
import com.l2i.siteL2I.model.chat.Forum;
import com.l2i.siteL2I.model.classroom.Classroom;
import com.l2i.siteL2I.model.person.Professor;
import com.l2i.siteL2I.repository.chat.ForumRepository;
import com.l2i.siteL2I.repository.classroom.ClassroomRepository;
import com.l2i.siteL2I.repository.person.ProfessorRepository;
import com.l2i.siteL2I.service.chat.ForumService;
import com.l2i.siteL2I.service.chat.MessageService;
import com.l2i.siteL2I.service.classroom.ClassroomService;
import com.l2i.siteL2I.service.classroom.CourseService;
import com.l2i.siteL2I.service.person.ProfessorService;
import com.l2i.siteL2I.service.person.StudentService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class SiteL2IApplication implements CommandLineRunner {

	private final ForumService forumService;
	private final MessageService messageService;
	private final StudentService studentService;
	private final ProfessorService professorService;
	private final ClassroomService classroomService;
	private final CourseService courseService;

	private final ForumRepository forumRepository;
	private final ClassroomRepository classroomRepository;
	private final ProfessorRepository professorRepository;

	public static void main(String[] args) {
		SpringApplication.run(SiteL2IApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Faker faker = new Faker();
		// Random random = new Random();

		// Create forums
		for (int i = 0; i < 10; i++) {
			ForumRequest forumRequest = new ForumRequest(faker.team().name());
			forumService.create(forumRequest);
		}

		// Retrieve all forums
		List<Forum> forums = forumRepository.findAll();
		
		// Create messages with random forums
		for (int j = 0; j < 10; j++) {
			// Forum randomForum = forums.get(random.nextInt(forums.size()));
			MessageRequest messageRequest = new MessageRequest(
				faker.lorem().characters(5, 25), 
				faker.number().numberBetween(1, forums.size()),
				null, 
				faker.name().firstName(), 
				faker.lorem().characters(5, 15)
			);

			messageService.create(messageRequest);
		}

		// Create classrooms
		for (int i = 0; i < 3; i++) {
			ClassroomRequest classroomRequest = new ClassroomRequest(
				faker.programmingLanguage().name(), 
				faker.lorem().characters(5, 10)
			);
			classroomService.create(classroomRequest);
		}

		// Retrieve all classrooms
		List<Classroom> classrooms = classroomRepository.findAll();

		// Create professors
		for (int i = 0; i < 15; i++) {
			// Classroom randomClassroom = classrooms.get(random.nextInt(classrooms.size()));
			ProfessorRequest professorRequest = new ProfessorRequest(
				faker.commerce().department(), 
				faker.lorem().characters(5, 10), 
				faker.number().numberBetween(1, classrooms.size()),
				null, 
				faker.name().lastName(),
				faker.internet().emailAddress(),
				faker.internet().password(),
				null,
				faker.lorem().characters(5, 10)
			);
			professorService.create(professorRequest);
		}

		// Retrieve all professors
		List<Professor> professors = professorRepository.findAll();

		// Create students
		for (int j = 0; j < 15; j++) {
			// Classroom classroom = classrooms.get(random.nextInt(classrooms.size()));

			StudentRequest studentRequest = new StudentRequest(
				faker.university().name(), 
				faker.number().numberBetween(1, classrooms.size()),
				faker.name().lastName(),
				faker.internet().emailAddress(),
				faker.internet().password(),
				null,
				faker.lorem().characters(5, 10)
			);
			studentService.create(studentRequest);
		}

		// Create courses
		for (int k = 0; k < 10; k++) {
			// Classroom classroom = classrooms.get(random.nextInt(classrooms.size()));
			// Professor professor = professors.get(random.nextInt(professors.size()));

			CourseRequest courseRequest = new CourseRequest(
				faker.book().title(), 
				faker.lorem().sentence(2), 
				faker.number().numberBetween(1, classrooms.size()),
				faker.number().numberBetween(1, professors.size()),
				faker.lorem().sentence(2)
			);
			courseService.create(courseRequest);
		}
	}

}
