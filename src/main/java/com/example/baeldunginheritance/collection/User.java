package com.example.baeldunginheritance.collection;

import com.example.baeldunginheritance.Utils.Role;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String uniName;
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private Role role;
//    @Indexed(unique = true)
    private String email;
//    @Size(max = 60)
    private String password;

    private Boolean enabled=false;


    public User(String firstName, String lastName, String uniName, Integer age, Role role, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    public User(String firstName, String lastName, String uniName, Integer age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
