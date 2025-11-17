package com.lcaohoanq.rsocketapi.controller;

import com.lcaohoanq.rsocketapi.client.RSocketClient;
import com.lcaohoanq.rsocketapi.model.Message;
import com.lcaohoanq.rsocketapi.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rsocket")
@RequiredArgsConstructor
public class RSocketTestController {
    
    private final RSocketClient rsocketClient;
    
    @GetMapping("/user/{id}")
    public Mono<UserResponse> getUser(@PathVariable Long id) {
        return rsocketClient.requestResponse(id);
    }
    
    @PostMapping("/send")
    public Mono<String> sendMessage(@RequestParam String message) {
        return rsocketClient.fireAndForget(message)
                .then(Mono.just("Message sent (fire-and-forget)"));
    }
    
    @GetMapping("/stream")
    public Flux<Message> streamMessages(@RequestParam(defaultValue = "test") String request) {
        return rsocketClient.requestStream(request);
    }
    
    @GetMapping("/users")
    public Flux<UserResponse> streamUsers() {
        return rsocketClient.streamUsers();
    }
}
