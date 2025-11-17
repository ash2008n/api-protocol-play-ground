package com.lcaohoanq.graphqlapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostInput {
    private String title;
    private String content;
    private Long userId;
}
