package com.example.baeldunginheritance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddInputsDTO {
    private String coruseCode;
    private String lectureHeader;
    private ArrayList<String> inputs;
}
