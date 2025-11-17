package com.lcaohoanq.webhookreceiver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookLog {
    private String eventId;
    private String eventType;
    private String source;
    private String status;
    private LocalDateTime receivedAt;
    private String payload;
}
