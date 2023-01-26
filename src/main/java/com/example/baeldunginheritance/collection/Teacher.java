package com.example.baeldunginheritance.collection;


import com.example.baeldunginheritance.Utils.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="teachers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Teacher{

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String uniName;
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(unique = true)
    private String email;
    @Column(length = 60)
    private String password;

//    private Boolean locked;
//
    private Boolean enabled=false;

    public Teacher(String firstName, String lastName, String uniName, Integer age, Role role, String email,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.role = role;
        this.email = email;
        this.password = password;
    }


}

