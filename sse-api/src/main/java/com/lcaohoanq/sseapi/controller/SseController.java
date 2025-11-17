package com.lcaohoanq.sseapi.controller;

import com.lcaohoanq.sseapi.model.ServerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/sse")
@Slf4j
public class SseController {
    
    private final Random random = new Random();
    
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ServerEvent>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {
                    ServerEvent event = new ServerEvent(
                            sequence,
                            "Event " + sequence,
                            LocalDateTime.now(),
                            "INFO"
                    );
                    log.info("Sending event: {}", sequence);
                    return ServerSentEvent.<ServerEvent>builder()
                            .id(String.valueOf(sequence))
                            .event("message")
                            .data(event)
                            .build();
                });
    }
    
    @GetMapping(value = "/stock-price", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<StockPrice>> streamStockPrice(@RequestParam(defaultValue = "AAPL") String symbol) {
        return Flux.interval(Duration.ofSeconds(2))
                .map(sequence -> {
                    double price = 100 + random.nextDouble() * 50;
                    StockPrice stockPrice = new StockPrice(
                            symbol,
                            price,
                            LocalDateTime.now()
                    );
                    
                    return ServerSentEvent.<StockPrice>builder()
                            .id(String.valueOf(sequence))
                            .event("stock-update")
                            .data(stockPrice)
                            .build();
                });
    }
    
    @GetMapping(value = "/countdown", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> countdown(@RequestParam(defaultValue = "10") int start) {
        return Flux.range(0, start + 1)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> {
                    int remaining = start - i;
                    return ServerSentEvent.<String>builder()
                            .id(String.valueOf(i))
                            .event("countdown")
                            .data(remaining == 0 ? "Finished!" : String.valueOf(remaining))
                            .build();
                });
    }
    
    @GetMapping(value = "/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamNotifications() {
        String[] notifications = {
            "New message received",
            "System update available",
            "Your report is ready",
            "Meeting reminder: 10 minutes",
            "Task completed successfully"
        };
        
        return Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> {
                    String message = notifications[random.nextInt(notifications.length)];
                    return ServerSentEvent.<String>builder()
                            .id(String.valueOf(sequence))
                            .event("notification")
                            .data(message)
                            .comment("Real-time notification")
                            .build();
                });
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class StockPrice {
    private String symbol;
    private double price;
    private LocalDateTime timestamp;
}
