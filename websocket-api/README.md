# WebSocket API Module

## Giới thiệu
Module WebSocket API sử dụng STOMP protocol để realtime messaging:
- WebSocket configuration với STOMP
- Chat application
- Broadcast notifications
- Scheduled notifications
- Interactive HTML client

## Cách chạy

```bash
cd websocket-api
mvn spring-boot:run
```

## Endpoints

- WebSocket: ws://localhost:8084/ws
- Web UI: http://localhost:8084
- REST API: http://localhost:8084/api/notify

## Test

### 1. Web Interface
Mở http://localhost:8084 trong nhiều tab browser để test chat

### 2. Send Notification via REST
```bash
curl -X POST http://localhost:8084/api/notify \
  -H "Content-Type: application/json" \
  -d '{"message":"Hello from REST","type":"INFO"}'
```

## STOMP Destinations

- `/app/chat.send` - Send chat message
- `/app/chat.join` - Join chat
- `/topic/public` - Chat messages (broadcast)
- `/topic/notifications` - Notifications (broadcast)
- `/queue/notifications` - User-specific notifications

## Tính năng

- **Real-time Chat**: Bidirectional communication
- **Broadcast Messages**: Send to all connected clients
- **Scheduled Notifications**: Auto send every 10s
- **SockJS Fallback**: Works without native WebSocket support
