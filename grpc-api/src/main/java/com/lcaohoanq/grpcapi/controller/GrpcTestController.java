package com.lcaohoanq.grpcapi.controller;

import com.lcaohoanq.grpcapi.client.UserGrpcClient;
import com.lcaohoanq.grpcapi.proto.UserListResponse;
import com.lcaohoanq.grpcapi.proto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grpc")
@RequiredArgsConstructor
public class GrpcTestController {
    
    private final UserGrpcClient grpcClient;
    
    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return grpcClient.getUserById(id);
    }
    
    @GetMapping("/users")
    public UserListResponse getAllUsers() {
        return grpcClient.getAllUsers();
    }
    
    @PostMapping("/users")
    public UserResponse createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String bio) {
        return grpcClient.createUser(name, email, bio != null ? bio : "");
    }
    
    @GetMapping("/users/stream")
    public String streamUsers() throws InterruptedException {
        grpcClient.streamUsers();
        return "Check server logs for streamed users";
    }
}
