# gRPC API Module

## Giới thiệu
Module gRPC API demo việc sử dụng gRPC trong Spring Boot:
- Protocol Buffer definitions (.proto files)
- gRPC server implementation
- gRPC client stub
- Server streaming
- REST endpoint để test gRPC calls

## Công nghệ sử dụng
- Spring Boot 3.4.0
- gRPC Spring Boot Starter
- Protocol Buffers (protobuf)
- grpc-java

## Cách chạy

### 1. Generate protobuf classes
```bash
cd grpc-api
mvn clean compile
```

### 2. Run application
```bash
mvn spring-boot:run
```

## Endpoints

- HTTP REST (for testing): http://localhost:8083
- gRPC Server: localhost:9090

## gRPC Service Methods

### 1. GetUserById (Unary RPC)
```protobuf
rpc GetUserById(UserRequest) returns (UserResponse);
```

### 2. GetAllUsers (Unary RPC)
```protobuf
rpc GetAllUsers(Empty) returns (UserListResponse);
```

### 3. CreateUser (Unary RPC)
```protobuf
rpc CreateUser(CreateUserRequest) returns (UserResponse);
```

### 4. StreamUsers (Server Streaming)
```protobuf
rpc StreamUsers(Empty) returns (stream UserResponse);
```

## Test qua REST API

### Get User by ID
```bash
curl http://localhost:8083/api/grpc/users/1
```

### Get All Users
```bash
curl http://localhost:8083/api/grpc/users
```

### Create User
```bash
curl -X POST "http://localhost:8083/api/grpc/users?name=Alice&email=alice@example.com&bio=Developer"
```

### Stream Users
```bash
curl http://localhost:8083/api/grpc/users/stream
```

## Test với grpcurl

### Install grpcurl
```bash
# macOS
brew install grpcurl

# Linux
go install github.com/fullstorydev/grpcurl/cmd/grpcurl@latest
```

### List Services
```bash
grpcurl -plaintext localhost:9090 list
```

### Get User by ID
```bash
grpcurl -plaintext -d '{"id": 1}' localhost:9090 user.UserService/GetUserById
```

### Get All Users
```bash
grpcurl -plaintext -d '{}' localhost:9090 user.UserService/GetAllUsers
```

### Create User
```bash
grpcurl -plaintext -d '{
  "name": "Charlie",
  "email": "charlie@example.com",
  "bio": "Backend Engineer"
}' localhost:9090 user.UserService/CreateUser
```

### Stream Users
```bash
grpcurl -plaintext -d '{}' localhost:9090 user.UserService/StreamUsers
```

## Protocol Buffer Schema

```protobuf
syntax = "proto3";

service UserService {
  rpc GetUserById(UserRequest) returns (UserResponse);
  rpc GetAllUsers(Empty) returns (UserListResponse);
  rpc CreateUser(CreateUserRequest) returns (UserResponse);
  rpc StreamUsers(Empty) returns (stream UserResponse);
}

message UserRequest {
  int64 id = 1;
}

message UserResponse {
  int64 id = 1;
  string name = 2;
  string email = 3;
  string bio = 4;
}
```

## Tính năng chính

### 1. Protocol Buffers
- Binary serialization (hiệu quả hơn JSON)
- Strong typing
- Backward/forward compatibility
- Code generation

### 2. gRPC Communication Patterns
- **Unary RPC**: Simple request-response
- **Server Streaming**: Server sends multiple responses
- **Client Streaming**: Client sends multiple requests (demo trong code)
- **Bidirectional Streaming**: Both send streams

### 3. Performance Benefits
- Binary protocol (nhỏ hơn JSON)
- HTTP/2 multiplexing
- Connection reuse
- Header compression

### 4. Spring Boot Integration
- `@GrpcService` annotation
- `@GrpcClient` injection
- Auto-configuration

## Cấu trúc code

```
grpc-api/
├── proto/
│   └── user_service.proto          # Protobuf definitions
├── client/
│   └── UserGrpcClient.java         # gRPC client
├── controller/
│   └── GrpcTestController.java     # REST endpoints for testing
├── model/
│   └── User.java                   # Domain model
└── service/
    ├── UserDataService.java        # Data service
    └── UserGrpcService.java        # gRPC service implementation
```

## So sánh gRPC vs REST

| Feature | REST | gRPC |
|---------|------|------|
| Protocol | HTTP/1.1 | HTTP/2 |
| Data format | JSON/XML | Protocol Buffers |
| API contract | OpenAPI | .proto files |
| Streaming | SSE/WebSocket | Built-in |
| Performance | Good | Excellent |
| Browser support | Native | Requires proxy |
| Learning curve | Easy | Moderate |
