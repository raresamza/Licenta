package com.example.baeldunginheritance.repository;

import com.example.baeldunginheritance.collection.Course;
import com.example.baeldunginheritance.collection.Quiz;
import com.example.baeldunginheritance.collection.QuizProblem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends MongoRepository<Quiz,String> {

    Quiz findByQuizCode(String quizCode);

}
