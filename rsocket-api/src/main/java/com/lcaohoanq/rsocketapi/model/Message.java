package com.lcaohoanq.rsocketapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private Long timestamp;
    
    public Message(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
}
