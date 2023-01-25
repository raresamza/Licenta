package com.example.baeldunginheritance.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private String firstName;
    private String lastName;
    private String uniName;
    private Integer age;
}
