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
    private Integer upvotes=0;
    private Integer downvotes=0;
    private ArrayList<String> upvoters=new ArrayList<>();
    private ArrayList<String> downvoters=new ArrayList<>();
    private ArrayList<String> inputs=new ArrayList<>();

    public void addComment(CommentDTO comment) {
        this.comments.add(comment);
    }

    public void addUpVoters(String voter) {
        this.upvoters.add(voter);
    }
    public void addDownVoters(String voter) {
        this.downvoters.add(voter);
    }

    public void fixUpvote(String email) {
        this.upvotes-=1;
        this.upvoters.remove(email);
    }

    public void fixDownvote(String email) {
        this.downvotes-=1;
        this.downvoters.remove(email);

    }

    public void addSolution(SolutionDTO solutionDTO) {
        this.solutions.add(solutionDTO);
    }

    public void upvote(){
        this.upvotes+=1;
    }

    public void downvote(){
        this.downvotes+=1;
    }

}


