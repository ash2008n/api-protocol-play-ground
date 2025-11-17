package com.lcaohoanq.websocketapi.scheduler;

import com.lcaohoanq.websocketapi.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();
    
    @Scheduled(fixedRate = 10000) // Every 10 seconds
    public void sendScheduledNotification() {
        String[] messages = {
            "System update available",
            "New user joined the platform",
            "Your report is ready",
            "Reminder: Meeting in 5 minutes"
        };
        
        String message = messages[random.nextInt(messages.length)];
        Notification notification = new Notification(message, "INFO", LocalDateTime.now());
        
        messagingTemplate.convertAndSend("/topic/notifications", notification);
        log.info("Sent scheduled notification: {}", message);
    }
}
