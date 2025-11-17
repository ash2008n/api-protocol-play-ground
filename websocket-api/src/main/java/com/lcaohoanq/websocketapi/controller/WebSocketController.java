package com.lcaohoanq.websocketapi.controller;

import com.lcaohoanq.websocketapi.model.ChatMessage;
import com.lcaohoanq.websocketapi.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
    
    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public ChatMessage joinUser(@Payload ChatMessage chatMessage, 
                                SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getFrom());
        return chatMessage;
    }
}

@RestController
@RequiredArgsConstructor
class NotificationController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    @PostMapping("/api/notify")
    public String sendNotification(@RequestBody Notification notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
        return "Notification sent: " + notification.getMessage();
    }
    
    @PostMapping("/api/notify/user")
    public String sendNotificationToUser(@RequestBody NotificationRequest request) {
        Notification notification = new Notification(request.getMessage(), request.getType());
        messagingTemplate.convertAndSendToUser(
                request.getUsername(), 
                "/queue/notifications", 
                notification
        );
        return "Notification sent to user: " + request.getUsername();
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class NotificationRequest {
    private String username;
    private String message;
    private String type;
}
