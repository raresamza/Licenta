package com.example.baeldunginheritance.DTO;

import com.example.baeldunginheritance.collection.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserToCourseDTO {
    private String courseCode;
    private String email;
}
