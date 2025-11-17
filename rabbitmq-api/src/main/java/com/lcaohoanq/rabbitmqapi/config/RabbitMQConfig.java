package com.lcaohoanq.rabbitmqapi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Value("${rabbitmq.queue.user-queue}")
    private String userQueue;
    
    @Value("${rabbitmq.queue.email-queue}")
    private String emailQueue;
    
    @Value("${rabbitmq.queue.notification-queue}")
    private String notificationQueue;
    
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
    
    @Value("${rabbitmq.routing.key.notification}")
    private String notificationRoutingKey;
    
    // Queues
    @Bean
    public Queue userQueue() {
        return new Queue(userQueue, true); // durable
    }
    
    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue, true);
    }
    
    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }
    
    // Exchanges
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }
    
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }
    
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }
    
    // Bindings - Direct Exchange
    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(directExchange())
                .with(userRoutingKey);
    }
    
    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(directExchange())
                .with(emailRoutingKey);
    }
    
    // Bindings - Topic Exchange
    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(topicExchange())
                .with(notificationRoutingKey);
    }
    
    // Bindings - Fanout Exchange (broadcasts to all queues)
    @Bean
    public Binding fanoutUserBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(fanoutExchange());
    }
    
    @Bean
    public Binding fanoutEmailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(fanoutExchange());
    }
    
    // Message Converter
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    // RabbitTemplate with JSON converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    
}
