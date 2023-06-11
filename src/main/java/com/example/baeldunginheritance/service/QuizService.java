package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.AddQuizDto;
import com.example.baeldunginheritance.DTO.AddQuizStudentsDTO;
import com.example.baeldunginheritance.DTO.QuizProblemDTO;
import com.example.baeldunginheritance.collection.Quiz;

import java.util.List;

public interface QuizService {


    Quiz addQuiz(AddQuizDto addQuizDto);

    Quiz getQuizByCode(String quizCode);

    List<Quiz> getQuizzes();

    Quiz addStudents(AddQuizStudentsDTO addQuizStudentsDTO);

    Quiz addProblem(QuizProblemDTO quizProblemDTO);
}
