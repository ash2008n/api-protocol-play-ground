package com.lcaohoanq.rabbitmqapi.consumer;

import com.lcaohoanq.rabbitmqapi.dto.MessageDto;
import com.lcaohoanq.rabbitmqapi.model.Message;
import com.lcaohoanq.rabbitmqapi.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    
    private final MessageRepository messageRepository;
    
    @RabbitListener(queues = "${rabbitmq.queue.user-queue}")
    public void consumeUserQueue(MessageDto messageDto) {
        log.info("Consumed from user-queue: {}", messageDto);
        saveMessage(messageDto, "user-queue");
    }
    
    @RabbitListener(queues = "${rabbitmq.queue.email-queue}")
    public void consumeEmailQueue(MessageDto messageDto) {
        log.info("Consumed from email-queue: {}", messageDto);
        saveMessage(messageDto, "email-queue");
        // Process email sending logic here
    }
    
    @RabbitListener(queues = "${rabbitmq.queue.notification-queue}")
    public void consumeNotificationQueue(MessageDto messageDto) {
        log.info("Consumed from notification-queue: {}", messageDto);
        saveMessage(messageDto, "notification-queue");
        // Process notification logic here
    }
    
    private void saveMessage(MessageDto messageDto, String queueName) {
        Message message = Message.builder()
                .messageId(messageDto.getId())
                .content(messageDto.getContent())
                .type(messageDto.getType())
                .queue(queueName)
                .receivedAt(LocalDateTime.now())
                .build();
        
        messageRepository.save(message);
        log.info("Message saved to database: {}", message);
    }
    
}
