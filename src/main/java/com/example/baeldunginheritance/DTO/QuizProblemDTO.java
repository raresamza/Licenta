package com.example.baeldunginheritance.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizProblemDTO {
    private String quizCode;
    private String problemText;
    private String problemHeader;
    private ArrayList<String> inputs;
    private String test;
}
