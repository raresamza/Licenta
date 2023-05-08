package com.example.baeldunginheritance.repository;

import com.example.baeldunginheritance.collection.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course,String> {

    public Course findByCourseCode(String courseCode);


}
