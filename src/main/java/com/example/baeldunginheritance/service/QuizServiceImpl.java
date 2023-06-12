package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.AddQuizDto;
import com.example.baeldunginheritance.DTO.AddQuizStudentsDTO;
import com.example.baeldunginheritance.DTO.QuizProblemDTO;
import com.example.baeldunginheritance.DTO.QuizProblemGetterDTO;
import com.example.baeldunginheritance.collection.Quiz;
import com.example.baeldunginheritance.collection.QuizProblem;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.repository.QuizRepository;
import com.example.baeldunginheritance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizServiceImpl(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Quiz addQuiz(AddQuizDto addQuizDto) {
        Quiz savedQuiz = new Quiz(addQuizDto.getQuizTitle());
        return quizRepository.save(savedQuiz);
    }

    @Override
    public Quiz getQuizByCode(String quizCode) {
        return quizRepository.findByQuizCode(quizCode);
    }

    @Override
    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz addStudents(AddQuizStudentsDTO addQuizStudentsDTO) {
        Quiz quiz = quizRepository.findByQuizCode(addQuizStudentsDTO.getQuizCode());

        if (quiz == null) {
            throw new IllegalStateException("quiz not found");
        }
        for (String email : addQuizStudentsDTO.getStudents()) {
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                throw new IllegalStateException("user not found");
            }
            System.out.println(user.isActiveQuiz());
            if (!user.isActiveQuiz()) {
                quiz.addUser(email);
                user.setEnrolledQuizCode(addQuizStudentsDTO.getQuizCode());
                user.setActiveQuiz(true);
                userRepository.save(user);
            }

        }

        return quizRepository.save(quiz);
    }

    @Override
    public Quiz addProblem(QuizProblemDTO quizProblemDTO) {
        Quiz quiz = quizRepository.findByQuizCode(quizProblemDTO.getQuizCode());
        if (quiz == null) {
            throw new IllegalStateException("quiz not found");
        }
        QuizProblem problem = new QuizProblem(genQuizProblemCode(),quizProblemDTO.getProblemHeader(), quizProblemDTO.getProblemText(), quizProblemDTO.getInputs(), quizProblemDTO.getTest());
        quiz.addQuizProblem(problem);
        return quizRepository.save(quiz);
    }

    @Override
    public QuizProblem getProblem(String quizCode, String quizProblemCode) {
        Quiz quiz=quizRepository.findByQuizCode(quizCode);
        if(quiz==null) {
            throw new IllegalStateException("quiz not found");
        }
        for(QuizProblem qp:quiz.getProblems()) {
            if (qp.getQuizProblemCode().equals(quizProblemCode)) {
                return qp;
            }
        }
        throw new IllegalStateException("quizProblem not found");
    }


    public String genQuizProblemCode() {
        StringBuilder buf = new StringBuilder();
        Random random = new Random();
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i <= 20; i++) {
            buf.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return buf.toString();

    }

}
