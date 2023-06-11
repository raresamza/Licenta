package com.example.baeldunginheritance.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizProblemDTO {
    private String quizCode;
    private String problemText;
    private String problemHeader;
}
