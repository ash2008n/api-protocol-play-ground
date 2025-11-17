package com.lcaohoanq.kafkaapi.consumer;

import com.lcaohoanq.kafkaapi.dto.UserEventDto;
import com.lcaohoanq.kafkaapi.model.User;
import com.lcaohoanq.kafkaapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    
    private final UserRepository userRepository;
    
    @KafkaListener(topics = "${kafka.topic.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserEvent(
            @Payload UserEventDto event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        
        log.info("Consumed user event from partition {}, offset {}: {}", partition, offset, event);
        
        // Process the event - save to database
        User user = User.builder()
                .id(event.getUserId())
                .name(event.getName())
                .email(event.getEmail())
                .action(event.getAction())
                .timestamp(event.getTimestamp())
                .build();
        
        userRepository.save(user);
        log.info("User event processed and saved to database: {}", user);
    }
    
    @KafkaListener(topics = "${kafka.topic.notifications}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNotification(@Payload String notification) {
        log.info("Consumed notification: {}", notification);
        // Process notification (e.g., send email, push notification, etc.)
    }
    
}
