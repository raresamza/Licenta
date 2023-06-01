package com.example.baeldunginheritance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentDTO {
    private String coruseCode;
    private String lectureHeader;
    private String comment;
}
