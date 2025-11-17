package com.lcaohoanq.graphqlapi.repository;

import com.lcaohoanq.graphqlapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
