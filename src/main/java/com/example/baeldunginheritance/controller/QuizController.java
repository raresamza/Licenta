package com.example.baeldunginheritance.controller;

import com.example.baeldunginheritance.DTO.AddQuizDto;
import com.example.baeldunginheritance.DTO.AddQuizStudentsDTO;
import com.example.baeldunginheritance.DTO.QuizProblemDTO;
import com.example.baeldunginheritance.DTO.QuizProblemGetterDTO;
import com.example.baeldunginheritance.collection.Quiz;
import com.example.baeldunginheritance.collection.QuizProblem;
import com.example.baeldunginheritance.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = {
        "http://localhost:3000/create-quiz",
        "http://localhost:3000/create-quiz/*",
        "http://localhost:3000/",
        "http://localhost:3000/*",
        "https://frotnend.vercel.app/",
        "https://frotnend.vercel.app/*",
        "http://localhost:3000/quiz-page",
        "http://localhost:3000/quiz-page/*",
        "http://localhost:3000/create-quiz"

})
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


    @PostMapping("/add")
    @ResponseBody
    public Quiz addQuiz(@RequestBody AddQuizDto addQuizDto) {
        return quizService.addQuiz(addQuizDto);
    }

    @GetMapping("/get/{quizCode}")
    public Quiz getQuizByCode(@PathVariable String quizCode) {
        return quizService.getQuizByCode(quizCode);
    }

    @GetMapping("/get")
    public List<Quiz> getQuizzes() {
        return quizService.getQuizzes();
    }

    @PutMapping("/add/students")
    public Quiz addStudents (@RequestBody AddQuizStudentsDTO addQuizStudentsDTO) {
        return quizService.addStudents(addQuizStudentsDTO);
    }

    @PutMapping("/add/problem")
    public Quiz addProblem(@RequestBody QuizProblemDTO quizProblemDTO) {
        return quizService.addProblem(quizProblemDTO);
    }

    @GetMapping("get/problem/{quizCode}/{quizProblemCode}")
    public QuizProblem getProblem(@PathVariable String quizCode,@PathVariable String quizProblemCode) {
        return quizService.getProblem(quizCode,quizProblemCode);
    }


}
