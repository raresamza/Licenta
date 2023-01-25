package com.example.baeldunginheritance.repository;


import com.example.baeldunginheritance.collection.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {
}
