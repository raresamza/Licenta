package com.example.baeldunginheritance.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTestDTO {
    private String coruseCode;
    private String lectureHeader;
    private String testCode;
}
