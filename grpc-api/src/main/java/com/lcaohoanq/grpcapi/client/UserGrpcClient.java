package com.lcaohoanq.grpcapi.client;

import com.lcaohoanq.grpcapi.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UserGrpcClient {
    
    @GrpcClient("userService")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;
    
    @GrpcClient("userService")
    private UserServiceGrpc.UserServiceStub asyncStub;
    
    public UserResponse getUserById(Long id) {
        UserRequest request = UserRequest.newBuilder()
                .setId(id)
                .build();
        
        return blockingStub.getUserById(request);
    }
    
    public UserListResponse getAllUsers() {
        return blockingStub.getAllUsers(Empty.newBuilder().build());
    }
    
    public UserResponse createUser(String name, String email, String bio) {
        CreateUserRequest request = CreateUserRequest.newBuilder()
                .setName(name)
                .setEmail(email)
                .setBio(bio)
                .build();
        
        return blockingStub.createUser(request);
    }
    
    public void streamUsers() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        asyncStub.streamUsers(Empty.newBuilder().build(), new StreamObserver<UserResponse>() {
            @Override
            public void onNext(UserResponse user) {
                log.info("Received user: {} - {}", user.getId(), user.getName());
            }
            
            @Override
            public void onError(Throwable t) {
                log.error("Error: {}", t.getMessage());
                latch.countDown();
            }
            
            @Override
            public void onCompleted() {
                log.info("Stream completed");
                latch.countDown();
            }
        });
        
        latch.await(10, TimeUnit.SECONDS);
    }
}
