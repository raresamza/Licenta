package com.example.baeldunginheritance.collection;


import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuizProblem {

    private String quizProblemCode="";
    private String problemHeader;
    private String problemText;
    private ArrayList<String> inputs=new ArrayList<>();
    private String testCode;
}
