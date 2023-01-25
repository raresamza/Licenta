package com.example.baeldunginheritance;

import com.example.baeldunginheritance.DTO.StudentDTO;
import com.example.baeldunginheritance.Utils.Role;
import com.example.baeldunginheritance.collection.Student;
import com.example.baeldunginheritance.collection.Teacher;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.repository.StudentRepository;
import com.example.baeldunginheritance.repository.TeacherRepository;
import com.example.baeldunginheritance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootApplication
public class BaeldungInheritanceApplication {




    public static void main(String[] args) {
        SpringApplication.run(BaeldungInheritanceApplication.class, args);
    }



    @Bean
    CommandLineRunner commandLineRunnerStudent(StudentRepository studentRepository, TeacherRepository teacherRepository, MongoTemplate mongoTemplate, UserRepository userRepository) {

        return args -> {

            Student student=new Student("Iacob","Catalin","UPT",21, Role.STUDENT,"cataliniacob@gmail.com","111");
            Teacher teacher=new Teacher("Pop","Nicolina","UPT",50, Role.TEACHER,"cataliniacob@gmail.com","111");
            User userT=new User("Pop","Nicolina","UPT",50, Role.TEACHER,"cataliniacob@gmail.com","111");
            User userS=new User("Iacob","Catalin","UPT",21, Role.STUDENT,"cataliniacob@gmail.com","111");

//            studentRepository.insert(student);
//            teacherRepository.insert(teacher);
//            userRepository.insert(userS);
//            userRepository.insert(userT);
        };

    }
}
