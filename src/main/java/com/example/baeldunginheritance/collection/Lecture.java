package com.example.baeldunginheritance.collection;


import com.example.baeldunginheritance.DTO.CommentDTO;
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


    public void addComment(CommentDTO comment) {
        this.comments.add(comment);
    }
}
