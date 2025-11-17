package com.lcaohoanq.rabbitmqapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto implements Serializable {
    
    private String id;
    private String content;
    private String type; // USER, EMAIL, NOTIFICATION
    private LocalDateTime timestamp;
    
}
