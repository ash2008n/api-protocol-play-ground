package com.lcaohoanq.kafkaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEventDto {
    
    private Long userId;
    private String name;
    private String email;
    private String action; // CREATED, UPDATED, DELETED
    private LocalDateTime timestamp;
    
}
