package com.lcaohoanq.restapi.service;

import com.lcaohoanq.restapi.dto.UserRequest;
import com.lcaohoanq.restapi.dto.UserResponse;
import com.lcaohoanq.restapi.entity.User;
import com.lcaohoanq.restapi.exception.ResourceNotFoundException;
import com.lcaohoanq.restapi.exception.DuplicateResourceException;
import com.lcaohoanq.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {
    
     List<UserResponse> getAllUsers();
     UserResponse getUserById(Long id);
     UserResponse createUser(UserRequest request);
     UserResponse updateUser(Long id, UserRequest request);
     void deleteUser(Long id);

}
