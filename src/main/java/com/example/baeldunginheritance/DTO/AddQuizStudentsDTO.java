package com.example.baeldunginheritance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddQuizStudentsDTO {
    private String quizCode;
    private ArrayList<String> students;
}
