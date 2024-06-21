package com.l2i.siteL2I.email.eventHandler;

import com.l2i.siteL2I.email.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CourseAddedListner {


    private final EmailService emailService;

    public CourseAddedListner(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void onCourseAdded(CourseAddedEvent event) {
        String to = event.getStudentMail();
        String subject = "Nouveau cours !!!" + event.getCourseTitle();
        String body = "Un nouveau cours vient d'etre ajoute. Titre : " + event.getCourseTitle();

        emailService.sendMail(new MultipartFile[]{}, to, new String[]{}, subject, body);
    }
}
