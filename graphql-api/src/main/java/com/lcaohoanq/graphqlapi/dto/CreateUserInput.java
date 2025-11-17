package com.lcaohoanq.graphqlapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInput {
    private String name;
    private String email;
    private String bio;
}
