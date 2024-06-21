package com.l2i.siteL2I.email.eventHandler;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CourseAddedEvent extends ApplicationEvent {

    private final String courseTitle;
    private final String studentMail;

    public CourseAddedEvent(Object source, String courseTitle, String studentMail) {
        super(source);
        this.courseTitle = courseTitle;
        this.studentMail = studentMail;
    }

}
