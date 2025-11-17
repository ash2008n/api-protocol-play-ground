package com.lcaohoanq.kafkaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApiApplication.class, args);
    }

}
