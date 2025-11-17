package com.lcaohoanq.webhookreceiver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcaohoanq.webhookreceiver.model.WebhookLog;
import com.lcaohoanq.webhookreceiver.model.WebhookPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WebhookService {
    
    @Value("${webhook.secret:my-secret-key}")
    private String webhookSecret;
    
    private final List<WebhookLog> logs = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public boolean verifySignature(WebhookPayload payload, String providedSignature) {
        if (providedSignature == null || providedSignature.isEmpty()) {
            log.warn("No signature provided");
            return false;
        }
        
        try {
            String payloadString = objectMapper.writeValueAsString(payload);
            String computedSignature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, webhookSecret)
                    .hmacHex(payloadString);
            
            log.debug("Computed signature: {}", computedSignature);
            log.debug("Provided signature: {}", providedSignature);
            
            // For demo purposes, also accept "valid-signature" as valid
            return computedSignature.equals(providedSignature) 
                    || "valid-signature".equals(providedSignature);
        } catch (Exception e) {
            log.error("Error verifying signature", e);
            return false;
        }
    }
    
    public void processPaymentWebhook(WebhookPayload payload) {
        log.info("Processing payment webhook: {}", payload.getEventType());
        
        WebhookLog webhookLog = new WebhookLog(
                payload.getEventId(),
                payload.getEventType(),
                "payment",
                "processed",
                LocalDateTime.now(),
                payload.getData()
        );
        
        logs.add(webhookLog);
        
        // Simulate payment processing
        switch (payload.getEventType()) {
            case "payment.success":
                log.info("Payment successful: {}", payload.getData());
                break;
            case "payment.failed":
                log.warn("Payment failed: {}", payload.getData());
                break;
            case "payment.refund":
                log.info("Payment refunded: {}", payload.getData());
                break;
            default:
                log.info("Unknown payment event: {}", payload.getEventType());
        }
    }
    
    public void processGithubWebhook(String event, String payload) {
        log.info("Processing GitHub webhook: {}", event);
        
        WebhookLog webhookLog = new WebhookLog(
                java.util.UUID.randomUUID().toString(),
                event,
                "github",
                "processed",
                LocalDateTime.now(),
                payload.substring(0, Math.min(100, payload.length()))
        );
        
        logs.add(webhookLog);
        
        switch (event) {
            case "push":
                log.info("GitHub push event received");
                break;
            case "pull_request":
                log.info("GitHub pull request event received");
                break;
            default:
                log.info("GitHub event: {}", event);
        }
    }
    
    public void processStripeWebhook(String payload, String signature) {
        log.info("Processing Stripe webhook");
        
        WebhookLog webhookLog = new WebhookLog(
                java.util.UUID.randomUUID().toString(),
                "stripe_event",
                "stripe",
                "processed",
                LocalDateTime.now(),
                payload.substring(0, Math.min(100, payload.length()))
        );
        
        logs.add(webhookLog);
    }
    
    public List<WebhookLog> getRecentLogs() {
        int size = logs.size();
        int fromIndex = Math.max(0, size - 50); // Last 50 logs
        return new ArrayList<>(logs.subList(fromIndex, size));
    }
}
