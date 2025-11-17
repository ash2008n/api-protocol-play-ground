package com.lcaohoanq.soapapi.endpoint;

import com.lcaohoanq.soapapi.generated.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.Map;

@Endpoint
@Slf4j
public class UserEndpoint {
    
    private static final String NAMESPACE_URI = "http://lcaohoanq.com/soapapi/users";
    
    private final Map<Long, User> users = new HashMap<>();
    
    public UserEndpoint() {
        // Initialize with sample data
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john@example.com");
        user1.setBio("Software Engineer");
        users.put(1L, user1);
        
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane@example.com");
        user2.setBio("Product Manager");
        users.put(2L, user2);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        log.info("SOAP request - getUserById: {}", request.getId());
        
        GetUserResponse response = new GetUserResponse();
        User user = users.get(request.getId());
        
        if (user == null) {
            user = new User();
            user.setId(request.getId());
            user.setName("User not found");
            user.setEmail("");
        }
        
        response.setUser(user);
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        log.info("SOAP request - getAllUsers");
        
        GetAllUsersResponse response = new GetAllUsersResponse();
        response.getUsers().addAll(users.values());
        return response;
    }
}
