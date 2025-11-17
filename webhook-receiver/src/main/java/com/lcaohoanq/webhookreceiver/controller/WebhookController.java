package com.lcaohoanq.webhookreceiver.controller;

import com.lcaohoanq.webhookreceiver.model.WebhookPayload;
import com.lcaohoanq.webhookreceiver.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {
    
    private final WebhookService webhookService;
    
    @PostMapping("/payment")
    public ResponseEntity<Map<String, String>> handlePaymentWebhook(
            @RequestHeader(value = "X-Webhook-Signature", required = false) String signature,
            @RequestBody WebhookPayload payload) {
        
        log.info("Received payment webhook: {}", payload);
        log.info("Signature: {}", signature);
        
        // Verify signature
        if (!webhookService.verifySignature(payload, signature)) {
            log.warn("Invalid signature for webhook: {}", payload.getEventId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid signature"));
        }
        
        // Process webhook
        webhookService.processPaymentWebhook(payload);
        
        return ResponseEntity.ok(Map.of(
                "status", "received",
                "eventId", payload.getEventId()
        ));
    }
    
    @PostMapping("/github")
    public ResponseEntity<Map<String, String>> handleGithubWebhook(
            @RequestHeader(value = "X-Hub-Signature-256", required = false) String signature,
            @RequestHeader(value = "X-GitHub-Event", required = false) String event,
            @RequestBody String payload) {
        
        log.info("Received GitHub webhook event: {}", event);
        log.info("Payload: {}", payload);
        
        webhookService.processGithubWebhook(event, payload);
        
        return ResponseEntity.ok(Map.of("status", "processed"));
    }
    
    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestHeader(value = "Stripe-Signature", required = false) String signature,
            @RequestBody String payload) {
        
        log.info("Received Stripe webhook");
        log.info("Signature: {}", signature);
        
        webhookService.processStripeWebhook(payload, signature);
        
        return ResponseEntity.ok("Success");
    }
    
    @GetMapping("/logs")
    public ResponseEntity<?> getWebhookLogs() {
        return ResponseEntity.ok(webhookService.getRecentLogs());
    }
}
