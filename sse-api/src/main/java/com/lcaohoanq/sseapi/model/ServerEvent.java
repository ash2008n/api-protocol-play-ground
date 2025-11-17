package com.lcaohoanq.sseapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerEvent {
    private Long id;
    private String message;
    private LocalDateTime timestamp;
    private String type;
}
