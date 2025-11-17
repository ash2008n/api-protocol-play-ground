package com.lcaohoanq.kafkaapi.controller;

import com.lcaohoanq.kafkaapi.dto.UserEventDto;
import com.lcaohoanq.kafkaapi.model.User;
import com.lcaohoanq.kafkaapi.producer.KafkaProducer;
import com.lcaohoanq.kafkaapi.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Kafka User API", description = "REST API to trigger Kafka messages")
public class UserController {
    
    private final KafkaProducer kafkaProducer;
    private final UserRepository userRepository;
    
    @PostMapping
    @Operation(summary = "Create user and publish event to Kafka")
    public ResponseEntity<String> createUser(@RequestBody UserEventDto userDto) {
        userDto.setAction("CREATED");
        userDto.setTimestamp(LocalDateTime.now());
        
        kafkaProducer.sendUserEvent(userDto);
        
        return ResponseEntity.ok("User creation event sent to Kafka");
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user and publish event to Kafka")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserEventDto userDto) {
        userDto.setUserId(id);
        userDto.setAction("UPDATED");
        userDto.setTimestamp(LocalDateTime.now());
        
        kafkaProducer.sendUserEvent(userDto);
        
        return ResponseEntity.ok("User update event sent to Kafka");
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user and publish event to Kafka")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        UserEventDto userDto = UserEventDto.builder()
                .userId(id)
                .action("DELETED")
                .timestamp(LocalDateTime.now())
                .build();
        
        kafkaProducer.sendUserEvent(userDto);
        
        return ResponseEntity.ok("User deletion event sent to Kafka");
    }
    
    @GetMapping
    @Operation(summary = "Get all users from database (consumed from Kafka)")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    
    @PostMapping("/notification")
    @Operation(summary = "Send a notification message to Kafka")
    public ResponseEntity<String> sendNotification(@RequestBody String message) {
        kafkaProducer.sendNotification(message);
        return ResponseEntity.ok("Notification sent to Kafka");
    }
    
}
