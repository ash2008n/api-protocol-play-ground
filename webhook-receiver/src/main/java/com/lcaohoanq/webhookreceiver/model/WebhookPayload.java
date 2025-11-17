package com.lcaohoanq.webhookreceiver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookPayload {
    private String eventId;
    private String eventType;
    private String data;
    private LocalDateTime timestamp;
}
