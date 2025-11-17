package com.lcaohoanq.rabbitmqapi.repository;

import com.lcaohoanq.rabbitmqapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByQueue(String queue);
}
