package com.lcaohoanq.rsocketapi.controller;

import com.lcaohoanq.rsocketapi.model.Message;
import com.lcaohoanq.rsocketapi.model.UserRequest;
import com.lcaohoanq.rsocketapi.model.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class RSocketController {
    
    private final Map<Long, UserResponse> users = new HashMap<>();
    
    public RSocketController() {
        users.put(1L, new UserResponse(1L, "John Doe", "john@example.com"));
        users.put(2L, new UserResponse(2L, "Jane Smith", "jane@example.com"));
        users.put(3L, new UserResponse(3L, "Bob Wilson", "bob@example.com"));
    }
    
    // Request-Response: Single request, single response
    @MessageMapping("request-response")
    public Mono<UserResponse> requestResponse(UserRequest request) {
        log.info("Request-Response: Get user with id {}", request.getId());
        
        UserResponse user = users.getOrDefault(
                request.getId(), 
                new UserResponse(request.getId(), "Unknown", "unknown@example.com")
        );
        
        return Mono.just(user);
    }
    
    // Fire-and-Forget: Send and don't wait for response
    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(Message message) {
        log.info("Fire-and-Forget: Received message: {}", message.getContent());
        // Process message without sending response
        return Mono.empty();
    }
    
    // Request-Stream: Single request, stream of responses
    @MessageMapping("request-stream")
    public Flux<Message> requestStream(Message request) {
        log.info("Request-Stream: Starting stream for request: {}", request.getContent());
        
        return Flux.interval(Duration.ofSeconds(1))
                .map(index -> new Message(
                        "Stream response #" + index + " for: " + request.getContent(),
                        System.currentTimeMillis()
                ))
                .take(10);
    }
    
    // Channel: Bidirectional stream
    @MessageMapping("channel")
    public Flux<Message> channel(Flux<Message> messages) {
        return messages
                .doOnNext(msg -> log.info("Channel: Received: {}", msg.getContent()))
                .map(msg -> new Message(
                        "Echo: " + msg.getContent(),
                        System.currentTimeMillis()
                ))
                .delayElements(Duration.ofMillis(500));
    }
    
    // Stream all users
    @MessageMapping("stream-users")
    public Flux<UserResponse> streamUsers() {
        log.info("Streaming all users");
        
        return Flux.fromIterable(users.values())
                .delayElements(Duration.ofSeconds(1));
    }
}
