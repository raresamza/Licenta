package com.example.baeldunginheritance.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private String courseCode;
    private String lectureHeader;
    private String email;
}
