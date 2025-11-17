package com.lcaohoanq.rabbitmqapi.controller;

import com.lcaohoanq.rabbitmqapi.dto.MessageDto;
import com.lcaohoanq.rabbitmqapi.model.Message;
import com.lcaohoanq.rabbitmqapi.producer.RabbitMQProducer;
import com.lcaohoanq.rabbitmqapi.repository.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "RabbitMQ Message API", description = "REST API to send messages to RabbitMQ")
public class MessageController {
    
    private final RabbitMQProducer producer;
    private final MessageRepository messageRepository;
    
    @PostMapping("/user")
    @Operation(summary = "Send message to user queue (Direct Exchange)")
    public ResponseEntity<String> sendToUser(@RequestBody String content) {
        MessageDto message = createMessage(content, "USER");
        producer.sendToUserQueue(message);
        return ResponseEntity.ok("Message sent to user queue");
    }
    
    @PostMapping("/email")
    @Operation(summary = "Send message to email queue (Direct Exchange)")
    public ResponseEntity<String> sendToEmail(@RequestBody String content) {
        MessageDto message = createMessage(content, "EMAIL");
        producer.sendToEmailQueue(message);
        return ResponseEntity.ok("Message sent to email queue");
    }
    
    @PostMapping("/topic")
    @Operation(summary = "Send message to topic exchange with routing key")
    public ResponseEntity<String> sendToTopic(
            @RequestParam String routingKey,
            @RequestBody String content) {
        MessageDto message = createMessage(content, "NOTIFICATION");
        producer.sendToTopic(routingKey, message);
        return ResponseEntity.ok("Message sent to topic exchange with routing key: " + routingKey);
    }
    
    @PostMapping("/broadcast")
    @Operation(summary = "Broadcast message to all queues (Fanout Exchange)")
    public ResponseEntity<String> broadcast(@RequestBody String content) {
        MessageDto message = createMessage(content, "BROADCAST");
        producer.broadcast(message);
        return ResponseEntity.ok("Message broadcasted to all queues");
    }
    
    @GetMapping
    @Operation(summary = "Get all messages from database")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageRepository.findAll());
    }
    
    @GetMapping("/queue/{queueName}")
    @Operation(summary = "Get messages by queue name")
    public ResponseEntity<List<Message>> getMessagesByQueue(@PathVariable String queueName) {
        return ResponseEntity.ok(messageRepository.findByQueue(queueName));
    }
    
    private MessageDto createMessage(String content, String type) {
        return MessageDto.builder()
                .id(UUID.randomUUID().toString())
                .content(content)
                .type(type)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
}
