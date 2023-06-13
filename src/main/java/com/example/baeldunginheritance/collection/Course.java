package com.example.baeldunginheritance.collection;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Document(collection="courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Course {

    @Id
    private String id;

    private String courseCode;

    private String courseDescription;
    private String title;

    private List<Lecture> lectures;

    private List<String> usersEmails;


    public Course(String description,String title) {
        this.courseCode=genCode();
        this.title=title;
        this.courseDescription =description;
        this.lectures=new ArrayList<>();
        this.usersEmails=new ArrayList<>();
    }



    public String genCode() {
        StringBuilder buf = new StringBuilder();
        Random random = new Random();
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i <= 10; i++) {
            buf.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return buf.toString();
    }

    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }
    public void addUser(String userEmail) {
        this.usersEmails.add(userEmail);
    }
}



