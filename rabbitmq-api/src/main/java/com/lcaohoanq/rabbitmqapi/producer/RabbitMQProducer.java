package com.lcaohoanq.rabbitmqapi.producer;

import com.lcaohoanq.rabbitmqapi.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.direct}")
    private String directExchange;
    
    @Value("${rabbitmq.exchange.topic}")
    private String topicExchange;
    
    @Value("${rabbitmq.exchange.fanout}")
    private String fanoutExchange;
    
    @Value("${rabbitmq.routing.key.user}")
    private String userRoutingKey;
    
    @Value("${rabbitmq.routing.key.email}")
    private String emailRoutingKey;
    
    // Direct Exchange - specific routing
    public void sendToUserQueue(MessageDto message) {
        log.info("Sending message to user queue: {}", message);
        rabbitTemplate.convertAndSend(directExchange, userRoutingKey, message);
    }
    
    public void sendToEmailQueue(MessageDto message) {
        log.info("Sending message to email queue: {}", message);
        rabbitTemplate.convertAndSend(directExchange, emailRoutingKey, message);
    }
    
    // Topic Exchange - pattern-based routing
    public void sendToTopic(String routingKey, MessageDto message) {
        log.info("Sending message to topic exchange with routing key {}: {}", routingKey, message);
        rabbitTemplate.convertAndSend(topicExchange, routingKey, message);
    }
    
    // Fanout Exchange - broadcast to all queues
    public void broadcast(MessageDto message) {
        log.info("Broadcasting message to all queues: {}", message);
        rabbitTemplate.convertAndSend(fanoutExchange, "", message);
    }
    
}
