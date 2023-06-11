package com.example.baeldunginheritance.collection;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Document(collection="quizzes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Quiz {
    @Id
    private String id;

    private String quizTitle;

    private String quizCode;

    private List<QuizProblem> problems=new ArrayList<>();

    private List<String> usersEmails=new ArrayList<>();


    public Quiz(String title){
        this.quizCode=genCode();
        this.quizTitle=title;
    }


    public void addUser(String user){
        this.usersEmails.add(user);
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

public void addQuizProblem(QuizProblem quizProblem) {
        this.problems.add(quizProblem);
}

}






