# RabbitMQ API Module

Demo of RabbitMQ messaging with Spring Boot, including multiple exchange types, queues, and routing patterns.

## Features

- **Direct Exchange**: Point-to-point routing with specific routing keys
- **Topic Exchange**: Pattern-based routing with wildcard support
- **Fanout Exchange**: Broadcast messages to all bound queues
- **Multiple Queues**: user-queue, email-queue, notification-queue
- **Message Persistence**: Save consumed messages to H2 database
- **REST API**: HTTP endpoints to publish messages
- **JSON Serialization**: Automatic JSON message conversion
- **Swagger UI**: Interactive API documentation

## Prerequisites

**RabbitMQ must be running locally:**

```bash
# Using Docker (recommended)
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Or install RabbitMQ locally
# Default credentials: guest/guest
```

**RabbitMQ Management UI**: http://localhost:15672

## Port

- HTTP Server: `8089`
- RabbitMQ: `localhost:5672` (AMQP)
- RabbitMQ Management: `localhost:15672` (HTTP)

## Running the Application

```bash
cd rabbitmq-api
mvn spring-boot:run
```

## Access Points

- **Swagger UI**: http://localhost:8089/swagger-ui.html
- **H2 Console**: http://localhost:8089/h2-console
  - JDBC URL: `jdbc:h2:mem:rabbitmqdb`
  - Username: `sa`
  - Password: (empty)
- **RabbitMQ Management**: http://localhost:15672
  - Username: `guest`
  - Password: `guest`

## API Endpoints

### 1. Send to User Queue (Direct Exchange)
```bash
curl -X POST http://localhost:8089/api/messages/user \
  -H "Content-Type: application/json" \
  -d '"New user registered"'
```

### 2. Send to Email Queue (Direct Exchange)
```bash
curl -X POST http://localhost:8089/api/messages/email \
  -H "Content-Type: application/json" \
  -d '"Send welcome email"'
```

### 3. Send to Topic Exchange with Pattern
```bash
# Will match routing.# pattern and go to notification-queue
curl -X POST "http://localhost:8089/api/messages/topic?routingKey=routing.notification.email" \
  -H "Content-Type: application/json" \
  -d '"Important notification"'
```

### 4. Broadcast to All Queues (Fanout)
```bash
curl -X POST http://localhost:8089/api/messages/broadcast \
  -H "Content-Type: application/json" \
  -d '"System maintenance notification"'
```

### 5. Get All Messages
```bash
curl http://localhost:8089/api/messages
```

### 6. Get Messages by Queue
```bash
curl http://localhost:8089/api/messages/queue/user-queue
```

## How It Works

### 1. Direct Exchange
- **Pattern**: Exact match routing
- **Use Case**: Send specific message to specific queue
- **Example**: User events → user-queue, Email tasks → email-queue

### 2. Topic Exchange
- **Pattern**: Wildcard routing (# = zero or more words, * = one word)
- **Use Case**: Pattern-based message routing
- **Example**: `routing.#` matches `routing.notification`, `routing.notification.email`, etc.

### 3. Fanout Exchange
- **Pattern**: Broadcast to all bound queues
- **Use Case**: Send same message to multiple consumers
- **Example**: System announcements, cache invalidation

## Exchange Types Comparison

| Exchange Type | Routing | Use Case |
|--------------|---------|----------|
| Direct | Exact key match | Point-to-point messaging |
| Topic | Pattern matching | Flexible routing with wildcards |
| Fanout | No routing key | Broadcast to all queues |
| Headers | Header attributes | Complex routing based on headers |

## Architecture

```
Producer → Exchange → Queue → Consumer → Database
                ↓
            Routing Key
```

## Message Flow

1. **Producer**: REST endpoint receives HTTP request
2. **Exchange**: Routes message based on type and routing key
3. **Queue**: Stores message until consumed
4. **Consumer**: Processes message and saves to database

## RabbitMQ Management

View queues, exchanges, and messages in real-time:
- Navigate to http://localhost:15672
- Login with guest/guest
- Check Queues, Exchanges, and Connections tabs

## Configuration

Key properties in `application.properties`:

```properties
# RabbitMQ Connection
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Queues
rabbitmq.queue.user-queue=user-queue
rabbitmq.queue.email-queue=email-queue
rabbitmq.queue.notification-queue=notification-queue

# Exchanges
rabbitmq.exchange.direct=direct-exchange
rabbitmq.exchange.topic=topic-exchange
rabbitmq.exchange.fanout=fanout-exchange
```

## Key Concepts

- **Queue**: Buffer that stores messages
- **Exchange**: Routes messages to queues based on rules
- **Binding**: Link between exchange and queue with routing key
- **Routing Key**: Message attribute used for routing
- **Message**: Data sent between producers and consumers
- **Durable**: Queue/message survives broker restart

## Testing Patterns

```bash
# Test Direct Exchange
curl -X POST http://localhost:8089/api/messages/user -d '"Direct message"' -H "Content-Type: application/json"

# Test Topic Exchange (wildcards)
curl -X POST "http://localhost:8089/api/messages/topic?routingKey=routing.user.created" -d '"Topic message"' -H "Content-Type: application/json"
curl -X POST "http://localhost:8089/api/messages/topic?routingKey=routing.email.sent" -d '"Another topic"' -H "Content-Type: application/json"

# Test Fanout Exchange (broadcast)
curl -X POST http://localhost:8089/api/messages/broadcast -d '"Broadcast message"' -H "Content-Type: application/json"
```

## Technologies

- Spring Boot 3.4.0
- Spring AMQP (RabbitMQ)
- RabbitMQ 3.x
- H2 Database
- Lombok
- Swagger/OpenAPI

## Troubleshooting

If RabbitMQ is not running:
```bash
# Check if RabbitMQ is running
docker ps | grep rabbitmq

# Start RabbitMQ
docker start rabbitmq

# Or run new container
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
