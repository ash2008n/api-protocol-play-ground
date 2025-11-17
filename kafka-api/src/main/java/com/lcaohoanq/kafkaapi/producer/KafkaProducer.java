package com.lcaohoanq.kafkaapi.producer;

import com.lcaohoanq.kafkaapi.dto.UserEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    
    private final KafkaTemplate<String, UserEventDto> kafkaTemplate;
    
    @Value("${kafka.topic.user-events}")
    private String userEventsTopic;
    
    public void sendUserEvent(UserEventDto event) {
        log.info("Sending user event to Kafka topic {}: {}", userEventsTopic, event);
        
        Message<UserEventDto> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, userEventsTopic)
                .setHeader(KafkaHeaders.KEY, String.valueOf(event.getUserId()))
                .build();
        
        kafkaTemplate.send(message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent successfully: offset={}, partition={}", 
                                result.getRecordMetadata().offset(),
                                result.getRecordMetadata().partition());
                    } else {
                        log.error("Failed to send message", ex);
                    }
                });
    }
    
    public void sendNotification(String notification) {
        log.info("Sending notification: {}", notification);
        UserEventDto event = UserEventDto.builder()
                .userId(0L)
                .name("System")
                .email("system@example.com")
                .action("NOTIFICATION")
                .timestamp(java.time.LocalDateTime.now())
                .build();
        event.setName(notification);
        kafkaTemplate.send("notifications", event);
    }
    
}
