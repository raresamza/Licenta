package com.example.baeldunginheritance.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreationDTO {

    private String courseDescription;
    private String title;
}
