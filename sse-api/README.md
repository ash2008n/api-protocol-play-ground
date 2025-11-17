# SSE (Server-Sent Events) API Module

## Giới thiệu
Module SSE demo việc streaming data từ server đến client qua HTTP:
- Server streaming với Flux
- Multiple event streams
- Stock price updates simulation
- Countdown timer
- Real-time notifications

## Cách chạy

```bash
cd sse-api
mvn spring-boot:run
```

## Endpoints

- Web UI: http://localhost:8085
- `/api/sse/stream` - Basic event stream (1/second)
- `/api/sse/stock-price?symbol=AAPL` - Stock updates (2/seconds)
- `/api/sse/countdown?start=10` - Countdown timer
- `/api/sse/notifications` - Random notifications (5/seconds)

## Test với cURL

```bash
# Basic stream
curl -N http://localhost:8085/api/sse/stream

# Stock price
curl -N http://localhost:8085/api/sse/stock-price?symbol=GOOGL

# Countdown
curl -N http://localhost:8085/api/sse/countdown?start=5
```

## So sánh SSE vs WebSocket

| Feature | SSE | WebSocket |
|---------|-----|-----------|
| Direction | Server → Client only | Bidirectional |
| Protocol | HTTP | WebSocket protocol |
| Reconnection | Automatic | Manual |
| Browser support | Excellent | Excellent |
| Use case | Live updates, feeds | Chat, gaming |
| Complexity | Simple | More complex |
