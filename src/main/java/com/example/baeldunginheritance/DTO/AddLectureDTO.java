package com.example.baeldunginheritance.DTO;

import com.example.baeldunginheritance.collection.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddLectureDTO {
    private String courseCode;
    private Lecture lecture;
}
