package com.lcaohoanq.grpcapi.service;

import com.lcaohoanq.grpcapi.model.User;
import com.lcaohoanq.grpcapi.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {
    
    private final UserDataService userDataService;
    
    @Override
    public void getUserById(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userDataService.getUserById(request.getId());
        
        if (user != null) {
            UserResponse response = UserResponse.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setBio(user.getBio())
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription("User not found with id: " + request.getId())
                            .asRuntimeException()
            );
        }
    }
    
    @Override
    public void getAllUsers(Empty request, StreamObserver<UserListResponse> responseObserver) {
        List<User> users = userDataService.getAllUsers();
        
        UserListResponse.Builder builder = UserListResponse.newBuilder();
        for (User user : users) {
            UserResponse userResponse = UserResponse.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setBio(user.getBio())
                    .build();
            builder.addUsers(userResponse);
        }
        
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
    
    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userDataService.createUser(
                request.getName(),
                request.getEmail(),
                request.getBio()
        );
        
        UserResponse response = UserResponse.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setBio(user.getBio())
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
    public void streamUsers(Empty request, StreamObserver<UserResponse> responseObserver) {
        List<User> users = userDataService.getAllUsers();
        
        // Stream users one by one
        for (User user : users) {
            UserResponse response = UserResponse.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setBio(user.getBio())
                    .build();
            
            responseObserver.onNext(response);
            
            // Simulate delay for streaming effect
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        responseObserver.onCompleted();
    }
}
