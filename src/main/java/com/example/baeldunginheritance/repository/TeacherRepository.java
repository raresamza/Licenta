package com.example.baeldunginheritance.repository;


import com.example.baeldunginheritance.collection.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher,String> {
}
