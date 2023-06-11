package com.example.baeldunginheritance.collection;

import com.example.baeldunginheritance.Utils.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {

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

    private String photoURL;

    private List<JWTToken> tokens;

    private List<Course> courses;

    private boolean activeQuiz=false;
    private String enrolledQuizCode="";


    public User(String firstName, String lastName, String uniName, Integer age, Role role, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.role = role;
        this.email = email;
        this.password = password;
        this.courses=new ArrayList<>();
    }
    public User(String firstName, String lastName, String uniName, Integer age, Role role, String email, String password,String photoURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.role = role;
        this.email = email;
        this.password = password;
        this.photoURL=photoURL;
        this.courses=new ArrayList<>();
    }
    public User(String firstName, String lastName, String uniName, Integer age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.courses=new ArrayList<>();
    }

    public User(String firstName, String lastName, String uniName, Integer age, String email, String password,String photoURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniName = uniName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.photoURL=photoURL;
        this.courses=new ArrayList<>();
    }

    public void addCourse(Course course) {
        System.out.println("in add before add:"+this.getCourses());
        this.courses.add(course);
        System.out.println("in add after add: "+this.getCourses());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(List.of(new SimpleGrantedAuthority(role.name()).toString()));

        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return this.enabled;
        return true;
    }
    @Override
    public String getPassword() {
        return password;
    }


}
