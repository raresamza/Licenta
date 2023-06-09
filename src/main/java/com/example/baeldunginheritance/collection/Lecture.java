package com.example.baeldunginheritance.collection;


import com.example.baeldunginheritance.DTO.CommentDTO;
import com.example.baeldunginheritance.DTO.SolutionDTO;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Lecture {
    private String header;
    private String content;
    private String problemHeader;
    private String problemContent;
    private ArrayList<CommentDTO> comments=new ArrayList<>();
    private ArrayList<SolutionDTO> solutions=new ArrayList<>();
    private String test;


    public void addComment(CommentDTO comment) {
        this.comments.add(comment);
    }

    public void addSolution(SolutionDTO solutionDTO) {
        this.solutions.add(solutionDTO);
    }

}


