# Kafka API Module

Demo of Apache Kafka messaging with Spring Boot, including producers, consumers, and topic management.

## Features

- **Kafka Producer**: Send messages to Kafka topics
- **Kafka Consumer**: Listen and process messages from Kafka topics
- **Topic Configuration**: Auto-create topics with partitions
- **REST API**: HTTP endpoints to trigger Kafka messages
- **Event Processing**: Process user events and save to H2 database
- **JSON Serialization**: Automatic JSON serialization/deserialization
- **Swagger UI**: Interactive API documentation

## Prerequisites

**Apache Kafka must be running locally:**

```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka (in another terminal)
bin/kafka-server-start.sh config/server.properties
```

## Port

- HTTP Server: `8088`
- Kafka Broker: `localhost:9092` (default)

## Running the Application

```bash
cd kafka-api
mvn spring-boot:run
```

## Access Points

- **Swagger UI**: http://localhost:8088/swagger-ui.html
- **H2 Console**: http://localhost:8088/h2-console
  - JDBC URL: `jdbc:h2:mem:kafkadb`
  - Username: `sa`
  - Password: (empty)

## API Endpoints

### 1. Create User (Publish to Kafka)
```bash
curl -X POST http://localhost:8088/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "name": "John Doe",
    "email": "john@example.com"
  }'
```

### 2. Update User (Publish to Kafka)
```bash
curl -X PUT http://localhost:8088/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com"
  }'
```

### 3. Delete User (Publish to Kafka)
```bash
curl -X DELETE http://localhost:8088/api/users/1
```

### 4. Get All Users (from Database)
```bash
curl http://localhost:8088/api/users
```

### 5. Send Notification
```bash
curl -X POST http://localhost:8088/api/users/notification \
  -H "Content-Type: application/json" \
  -d '"Welcome to Kafka API!"'
```

## How It Works

1. **Producer Flow**:
   - REST endpoint receives HTTP request
   - KafkaProducer publishes event to topic
   - Message contains user data in JSON format

2. **Consumer Flow**:
   - KafkaConsumer listens to topic
   - Receives message automatically
   - Processes event and saves to H2 database

3. **Topics**:
   - `user-events`: User CRUD operations (3 partitions)
   - `notifications`: General notifications (2 partitions)

## Kafka Commands (Optional)

```bash
# List topics
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

# Describe topic
bin/kafka-topics.sh --describe --topic user-events --bootstrap-server localhost:9092

# Console consumer (to see messages)
bin/kafka-console-consumer.sh --topic user-events --from-beginning --bootstrap-server localhost:9092

# Console producer (send test messages)
bin/kafka-console-producer.sh --topic user-events --bootstrap-server localhost:9092
```

## Configuration

Key properties in `application.properties`:

```properties
# Kafka Broker
spring.kafka.bootstrap-servers=localhost:9092

# Consumer Config
spring.kafka.consumer.group-id=kafka-api-group
spring.kafka.consumer.auto-offset-reset=earliest

# Producer/Consumer Serialization
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
```

## Key Concepts

- **Topic**: Named stream of records
- **Partition**: Topics are split into partitions for parallelism
- **Producer**: Publishes messages to topics
- **Consumer**: Subscribes to topics and processes messages
- **Consumer Group**: Multiple consumers sharing the workload
- **Offset**: Sequential ID of messages in a partition

## Testing Without Kafka

If Kafka is not running, the application will start but:
- Producer calls will fail
- Consumer won't receive messages
- Topics won't be created

For testing, use Docker:
```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:latest
```

## Technologies

- Spring Boot 3.4.0
- Spring Kafka
- Apache Kafka
- H2 Database
- Lombok
- Swagger/OpenAPI
