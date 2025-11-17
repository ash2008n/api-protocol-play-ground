# RSocket API Module

## Giới thiệu
Module RSocket API demo reactive messaging với RSocket:
- Request-Response pattern
- Fire-and-Forget pattern
- Request-Stream pattern
- Channel (bidirectional streaming)
- RSocket client implementation

## Cách chạy

```bash
cd rsocket-api
mvn spring-boot:run
```

## Endpoints

- RSocket Server: tcp://localhost:7000
- HTTP REST (for testing): http://localhost:8088

## RSocket Communication Patterns

### 1. Request-Response
Single request → Single response
```bash
curl http://localhost:8088/api/rsocket/user/1
```

### 2. Fire-and-Forget
Send message without waiting for response
```bash
curl -X POST "http://localhost:8088/api/rsocket/send?message=Hello"
```

### 3. Request-Stream
Single request → Stream of responses
```bash
curl http://localhost:8088/api/rsocket/stream?request=test
```

### 4. Stream Users
```bash
curl http://localhost:8088/api/rsocket/users
```

## Test với RSocket CLI

### Install RSocket CLI
```bash
# macOS
brew install rsocket-cli

# Or download from GitHub
```

### Request-Response
```bash
rsocket-cli --request --data '{"id":1}' --route request-response \
  --dataFormat json tcp://localhost:7000
```

### Fire-and-Forget
```bash
rsocket-cli --fnf --data '{"content":"Hello","timestamp":0}' \
  --route fire-and-forget --dataFormat json tcp://localhost:7000
```

### Request-Stream
```bash
rsocket-cli --stream --data '{"content":"test","timestamp":0}' \
  --route request-stream --dataFormat json tcp://localhost:7000
```

## RSocket Advantages

- **Reactive**: Built on Reactive Streams
- **Efficient**: Binary protocol
- **Bidirectional**: Full-duplex communication
- **Backpressure**: Flow control
- **Multiple patterns**: Request-Response, Streaming, etc.
- **Transport agnostic**: TCP, WebSocket, etc.

## Use Cases

- Microservices communication
- Real-time data streaming
- IoT device communication
- Gaming backends
- Financial data feeds
