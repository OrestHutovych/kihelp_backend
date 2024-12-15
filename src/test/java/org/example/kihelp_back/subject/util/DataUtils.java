package org.example.kihelp_back.subject.util;

import org.example.kihelp_back.subject.dto.SubjectRequest;
import org.example.kihelp_back.subject.model.Subject;

public class DataUtils {
    public static Subject getTestSubjectTransient(){
        Subject subject = new Subject();
        subject.setName("Test Subject");
        subject.setCourseNumber(1);
        return subject;
    }

    public static Subject getTestSubjectPersisted(){
        Subject subject = new Subject();
        subject.setId(1);
        subject.setName("Test Subject");
        subject.setCourseNumber(1);
        return subject;
    }

    public static Subject getTest2SubjectTransient(){
        Subject subject = new Subject();
        subject.setName("Test2 Subject");
        subject.setCourseNumber(2);
        return subject;
    }

    public static Subject getTest2SubjectPersisted(){
        Subject subject = new Subject();
        subject.setId(12);
        subject.setName("Test2 Subject");
        subject.setCourseNumber(2);
        return subject;
    }

    public static SubjectRequest getTestSubjectRequestTransient(){
       return new SubjectRequest(
               "Test Subject",
               1
       );
    }

    public static SubjectRequest getTestSubjectRequest2Transient(){
        return new SubjectRequest(
                "Test2 Subject",
                2
        );
    }
}
