package com.lcaohoanq.rsocketapi.client;

import com.lcaohoanq.rsocketapi.model.Message;
import com.lcaohoanq.rsocketapi.model.UserRequest;
import com.lcaohoanq.rsocketapi.model.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RSocketClient {
    
    private final RSocketRequester requester;
    
    public RSocketClient(RSocketRequester.Builder builder) {
        this.requester = builder
                .tcp("localhost", 7000);
    }
    
    public Mono<UserResponse> requestResponse(Long userId) {
        log.info("Client: Sending request-response for user {}", userId);
        
        return requester
                .route("request-response")
                .data(new UserRequest(userId))
                .retrieveMono(UserResponse.class)
                .doOnNext(response -> log.info("Client: Received response: {}", response));
    }
    
    public Mono<Void> fireAndForget(String message) {
        log.info("Client: Sending fire-and-forget: {}", message);
        
        return requester
                .route("fire-and-forget")
                .data(new Message(message))
                .send();
    }
    
    public Flux<Message> requestStream(String request) {
        log.info("Client: Starting request-stream for: {}", request);
        
        return requester
                .route("request-stream")
                .data(new Message(request))
                .retrieveFlux(Message.class)
                .doOnNext(msg -> log.info("Client: Stream received: {}", msg.getContent()));
    }
    
    public Flux<UserResponse> streamUsers() {
        log.info("Client: Requesting user stream");
        
        return requester
                .route("stream-users")
                .retrieveFlux(UserResponse.class)
                .doOnNext(user -> log.info("Client: Received user: {}", user.getName()));
    }
}
