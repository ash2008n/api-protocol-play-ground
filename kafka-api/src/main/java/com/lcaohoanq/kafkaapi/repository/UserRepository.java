package com.lcaohoanq.kafkaapi.repository;

import com.lcaohoanq.kafkaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
