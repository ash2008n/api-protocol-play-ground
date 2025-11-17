# API Protocol Playground 🚀

Một bộ sưu tập đầy đủ nhất về các API protocol phổ biến trong Spring Boot. Mỗi module là một ứng dụng độc lập, có thể chạy và test riêng biệt.

## 📚 Mục lục

- [Tổng quan](#tổng-quan)
- [Cấu trúc project](#cấu-trúc-project)
- [Các Module](#các-module)
- [Cách chạy](#cách-chạy)
- [So sánh các Protocol](#so-sánh-các-protocol)
- [Khi nào dùng Protocol nào?](#khi-nào-dùng-protocol-nào)

## 🎯 Tổng quan

Repository này được tạo ra để giúp developers hiểu và so sánh các API protocol khác nhau trong Spring Boot. Mỗi module demo một protocol cụ thể với code sạch, dễ hiểu và có thể chạy ngay.

### Công nghệ
- **Spring Boot**: 3.4.0
- **Java**: 17
- **Build Tool**: Maven
- **Database**: H2 (in-memory)

## 📁 Cấu trúc project

```
api-protocol-play-ground/
├── pom.xml                    # Parent POM
├── README.md                  # File này
├── rest-api/                  # Module 1: REST API
├── graphql-api/               # Module 2: GraphQL API
├── grpc-api/                  # Module 3: gRPC
├── websocket-api/             # Module 4: WebSocket STOMP
├── sse-api/                   # Module 5: Server-Sent Events
├── webhook-receiver/          # Module 6: Webhook Receiver
├── soap-api/                  # Module 7: SOAP Web Service
├── rsocket-api/               # Module 8: RSocket
├── kafka-api/                 # Module 9: Apache Kafka
├── rabbitmq-api/              # Module 10: RabbitMQ
└── odata-api/                 # Module 11: OData v4
```

## 🎨 Các Module

### 1. REST API (Port: 8081)
**Protocol truyền thống và phổ biến nhất**

- ✅ CRUD operations đầy đủ
- ✅ Request/Response validation
- ✅ Exception handling
- ✅ Swagger/OpenAPI documentation
- ✅ H2 database

📖 [Chi tiết →](rest-api/README.md)

```bash
cd rest-api && mvn spring-boot:run
# Swagger: http://localhost:8081/swagger-ui.html
```

### 2. GraphQL API (Port: 8082)
**Query language cho API - Flexible data fetching**

- ✅ Schema-first approach
- ✅ Query & Mutation operations
- ✅ DataLoader & BatchMapping (N+1 solution)
- ✅ Nested resolvers
- ✅ GraphiQL web interface

📖 [Chi tiết →](graphql-api/README.md)

```bash
cd graphql-api && mvn spring-boot:run
# GraphiQL: http://localhost:8082/graphiql
```

### 3. gRPC API (Port: 8083, gRPC: 9090)
**High-performance RPC framework - Binary protocol**

- ✅ Protocol Buffers (.proto)
- ✅ Server & Client implementation
- ✅ Unary RPC
- ✅ Server streaming
- ✅ REST endpoints cho testing

📖 [Chi tiết →](grpc-api/README.md)

```bash
cd grpc-api && mvn clean compile spring-boot:run
# REST: http://localhost:8083
# gRPC: localhost:9090
```

### 4. WebSocket API (Port: 8084)
**Real-time bidirectional communication**

- ✅ STOMP protocol
- ✅ Chat application
- ✅ Broadcast notifications
- ✅ Scheduled messages
- ✅ Interactive HTML client

📖 [Chi tiết →](websocket-api/README.md)

```bash
cd websocket-api && mvn spring-boot:run
# Web UI: http://localhost:8084
```

### 5. SSE API (Port: 8085)
**Server-Sent Events - One-way streaming**

- ✅ Server → Client streaming
- ✅ Multiple event sources
- ✅ Stock price simulation
- ✅ Countdown timer
- ✅ Auto-reconnection

📖 [Chi tiết →](sse-api/README.md)

```bash
cd sse-api && mvn spring-boot:run
# Web UI: http://localhost:8085
```

### 6. Webhook Receiver (Port: 8086)
**Event-driven communication - Callbacks**

- ✅ Payment webhooks
- ✅ GitHub/Stripe simulation
- ✅ HMAC signature verification
- ✅ Webhook logging
- ✅ Security best practices

📖 [Chi tiết →](webhook-receiver/README.md)

```bash
cd webhook-receiver && mvn spring-boot:run
# API: http://localhost:8086/webhook/*
```

### 7. SOAP API (Port: 8087)
**Enterprise web service standard**

- ✅ XSD schema definition
- ✅ Auto-generated WSDL
- ✅ SOAP endpoints
- ✅ JAXB data binding
- ✅ Contract-first approach

📖 [Chi tiết →](soap-api/README.md)

```bash
cd soap-api && mvn clean compile spring-boot:run
# WSDL: http://localhost:8087/ws/users.wsdl
```

### 8. RSocket API (Port: 8088, RSocket: 7000)
**Reactive messaging protocol**

- ✅ Request-Response
- ✅ Fire-and-Forget
- ✅ Request-Stream
- ✅ Bidirectional Channel
- ✅ Backpressure support

📖 [Chi tiết →](rsocket-api/README.md)

```bash
cd rsocket-api && mvn spring-boot:run
# HTTP: http://localhost:8088
# RSocket: tcp://localhost:7000
```

### 9. Kafka API (Port: 8088)
**Distributed event streaming platform - Pub/Sub messaging**

- ✅ Producer & Consumer
- ✅ Topics & Partitions
- ✅ Message queuing
- ✅ Event-driven architecture
- ✅ JSON serialization

📖 [Chi tiết →](kafka-api/README.md)

```bash
# Prerequisites: Kafka server running on localhost:9092
cd kafka-api && mvn spring-boot:run
# Swagger: http://localhost:8088/swagger-ui.html
```

### 10. RabbitMQ API (Port: 8089)
**Message broker - Advanced routing patterns**

- ✅ Direct, Topic, Fanout exchanges
- ✅ Multiple queues
- ✅ Message routing
- ✅ AMQP protocol
- ✅ Durable messages

📖 [Chi tiết →](rabbitmq-api/README.md)

```bash
# Prerequisites: RabbitMQ server running on localhost:5672
cd rabbitmq-api && mvn spring-boot:run
# Swagger: http://localhost:8089/swagger-ui.html
# RabbitMQ Management: http://localhost:15672
```

### 11. OData API (Port: 8090)
**RESTful protocol for querying data - Standardized API**

- ✅ OData v4 protocol
- ✅ $filter, $select, $orderby queries
- ✅ Service metadata ($metadata)
- ✅ Entity Data Model
- ✅ JSON format

📖 [Chi tiết →](odata-api/README.md)

```bash
cd odata-api && mvn spring-boot:run
# Service Root: http://localhost:8090/odata.svc/
# Metadata: http://localhost:8090/odata.svc/$metadata
# Products: http://localhost:8090/odata.svc/Products
```

## 🚀 Cách chạy

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose (for Kafka and RabbitMQ)

### 1️⃣ Start Infrastructure Services (Kafka & RabbitMQ)

#### Option A: Using Helper Script (Recommended)
```bash
# Start all services
./docker-manager.sh start

# Check status
./docker-manager.sh status

# View logs
./docker-manager.sh logs

# Restart services
./docker-manager.sh restart

# Stop services
./docker-manager.sh stop

# Clean up (stop + remove volumes)
./docker-manager.sh clean
```

#### Option B: Using Docker Compose Directly
```bash
# Start Kafka, Zookeeper, and RabbitMQ
docker compose up -d

# Verify services are running
docker compose ps

# View logs
docker compose logs -f kafka
docker compose logs -f rabbitmq

# Stop all services
docker compose down

# Stop and remove volumes
docker compose down -v
```

**Services Started:**
- 🟢 **Kafka**: `localhost:9092`
- 🟢 **Zookeeper**: `localhost:2181`
- 🟢 **Kafka UI**: `http://localhost:8080` (monitoring dashboard)
- 🟢 **RabbitMQ**: `localhost:5672` (AMQP)
- 🟢 **RabbitMQ Management UI**: `http://localhost:15672` (guest/guest)

### 2️⃣ Run Application Modules

#### Chạy tất cả modules
```bash
# Build tất cả
mvn clean install

# Chạy từng module (terminal riêng cho mỗi module)
mvn spring-boot:run -pl rest-api
mvn spring-boot:run -pl graphql-api
mvn spring-boot:run -pl grpc-api
# ... và các module khác
```

#### Chạy một module cụ thể
```bash
cd <module-name>
mvn clean install
mvn spring-boot:run
```

**Note:** Kafka and RabbitMQ modules require Docker services to be running first!

## 📊 So sánh các Protocol

| Protocol | Direction | Data Format | Performance | Complexity | Browser Support |
|----------|-----------|-------------|-------------|------------|-----------------|
| **REST** | Request-Response | JSON/XML | Good | Low | ✅ Excellent |
| **GraphQL** | Request-Response | JSON | Good | Medium | ✅ Excellent |
| **gRPC** | Bidirectional | Protobuf | Excellent | High | ⚠️ With proxy |
| **WebSocket** | Bidirectional | Text/Binary | Excellent | Medium | ✅ Excellent |
| **SSE** | Server→Client | Text | Good | Low | ✅ Excellent |
| **Webhook** | Event callback | JSON | N/A | Low | ❌ No |
| **SOAP** | Request-Response | XML | Fair | High | ✅ Good |
| **RSocket** | Bidirectional | Binary | Excellent | Medium | ⚠️ With proxy |
| **Kafka** | Pub/Sub | JSON/Binary | Excellent | Medium | ❌ No |
| **RabbitMQ** | Pub/Sub | JSON/Binary | Excellent | Medium | ❌ No |
| **OData** | Request-Response | JSON/XML | Good | Medium | ✅ Excellent |

## 🤔 Khi nào dùng Protocol nào?

### REST API
✅ **Dùng khi:**
- CRUD operations đơn giản
- Public API
- Mobile apps
- Microservices
- Cần caching HTTP

❌ **Không dùng khi:**
- Cần real-time updates
- Bandwidth limited
- Complex nested data requirements

---

### GraphQL
✅ **Dùng khi:**
- Client cần flexible queries
- Multiple resources in one request
- Mobile apps (giảm over-fetching)
- Complex data relationships

❌ **Không dùng khi:**
- Simple CRUD
- File uploads (complex)
- Cần HTTP caching
- Team chưa quen GraphQL

---

### gRPC
✅ **Dùng khi:**
- Microservices internal communication
- High performance required
- Streaming data
- Strong typing needed
- Polyglot systems

❌ **Không dùng khi:**
- Browser clients (cần proxy)
- Public API
- Human-readable format required

---

### WebSocket
✅ **Dùng khi:**
- Real-time chat
- Online gaming
- Collaborative editing
- Live dashboards
- Bidirectional communication

❌ **Không dùng khi:**
- Simple notifications (dùng SSE)
- One-way data flow
- Scalability concerns

---

### SSE (Server-Sent Events)
✅ **Dùng khi:**
- Live feeds (news, stocks)
- Notifications
- Progress updates
- Server → Client only
- Auto-reconnection needed

❌ **Không dùng khi:**
- Client needs to send data back
- Binary data
- IE support required

---

### Webhook
✅ **Dùng khi:**
- Third-party integrations
- Payment notifications
- GitHub/GitLab events
- Async processing
- Event-driven architecture

❌ **Không dùng khi:**
- Real-time sync needed
- Can't expose public endpoint
- Need immediate response

---

### SOAP
✅ **Dùng khi:**
- Enterprise systems
- Banking/Finance
- Legacy integration
- WS-Security needed
- ACID transactions required

❌ **Không dùng khi:**
- Modern web/mobile apps
- RESTful preferred
- JSON preferred
- Simple APIs

---

### RSocket
✅ **Dùng khi:**
- Reactive systems
- Microservices streaming
- IoT devices
- Gaming backends
- Backpressure handling needed

❌ **Không dùng khi:**
- Simple request-response
- Browser clients (limited support)
- Team unfamiliar with reactive

---

### Kafka
✅ **Dùng khi:**
- Event streaming platform
- High-throughput messaging
- Log aggregation
- Event sourcing
- Microservices communication
- Real-time analytics

❌ **Không dùng khi:**
- Simple point-to-point messaging
- Need immediate message delivery guarantee
- Small-scale applications
- Complex routing logic required

---

### RabbitMQ
✅ **Dùng khi:**
- Task queues
- Message routing with patterns
- Reliable message delivery
- Priority queues
- Request-reply patterns
- Legacy AMQP integration

❌ **Không dùng khi:**
- High-throughput streaming (use Kafka)
- Simple pub/sub (use SSE/WebSocket)
- Browser clients
- Real-time gaming

---

### OData
✅ **Dùng khi:**
- Standardized REST API
- Excel/PowerBI integration
- Complex query requirements
- CRUD operations
- Enterprise data services
- Metadata-driven clients

❌ **Không dùng khi:**
- Real-time updates needed
- GraphQL flexibility preferred
- Simple REST sufficient
- Team unfamiliar with OData conventions

---

## 📈 Performance Comparison

### Data Size (1000 requests)
```
gRPC (Protobuf):    ~50 KB
RSocket (Binary):   ~55 KB
REST (JSON):       ~150 KB
GraphQL (JSON):    ~120 KB (with optimization)
SOAP (XML):        ~300 KB
```

### Latency (Average)
```
RSocket:    < 5ms
gRPC:       < 10ms
WebSocket:  < 15ms
REST:       ~50ms
GraphQL:    ~60ms
SOAP:       ~100ms
```

## 🎓 Learning Path

Đề xuất thứ tự học:

1. **REST API** - Nền tảng cơ bản
2. **GraphQL** - Modern alternative  
3. **OData** - Standardized REST
4. **WebSocket** - Real-time basics
5. **SSE** - Simpler real-time
6. **Webhook** - Event-driven
7. **RabbitMQ** - Message queuing
8. **Kafka** - Event streaming
9. **gRPC** - High performance
10. **RSocket** - Reactive messaging
11. **SOAP** - Legacy systems

## 🔧 Tools hữu ích

- **REST**: Postman, curl
- **GraphQL**: GraphiQL, Apollo Client DevTools
- **gRPC**: grpcurl, BloomRPC
- **WebSocket**: Browser DevTools, websocat
- **SSE**: Browser DevTools, curl -N
- **Webhook**: ngrok, RequestBin
- **SOAP**: SoapUI, Postman
- **RSocket**: rsocket-cli
- **Kafka**: kafka-console-producer, kafka-console-consumer, Kafka Tool, Conduktor
- **RabbitMQ**: RabbitMQ Management UI, rabbitmqadmin, Postman
- **OData**: Postman, curl, Excel PowerQuery, LINQPad

## 📝 Best Practices

### REST
- Sử dụng proper HTTP methods
- Versioning API (/v1/, /v2/)
- HATEOAS cho discoverability
- Rate limiting

### GraphQL
- Implement DataLoader
- Query depth limiting
- Proper error handling
- Persisted queries for production

### gRPC
- Use streaming for large data
- Implement deadlines
- Proper error codes
- Load balancing

### WebSocket
- Implement heartbeat/ping-pong
- Graceful reconnection
- Message queuing
- Authentication

### Kafka
- Choose appropriate partition count
- Use proper serialization (Avro/JSON)
- Configure retention policies
- Monitor consumer lag
- Implement idempotent producers

### RabbitMQ
- Use durable queues for persistence
- Implement message acknowledgments
- Configure dead letter exchanges
- Set appropriate prefetch count
- Monitor queue length

### OData
- Implement $top/$skip pagination
- Support $filter/$orderby queries
- Use proper EDM types
- Enable CORS for browser clients
- Document metadata endpoint

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Open Pull Request

## 📄 License

MIT License - see LICENSE file

## 👤 Author

**lcaohoanq**
- Email: hoangcao.qlda@gmail.com
- GitHub: [@lcaohoanq](https://github.com/lcaohoanq)

## 🙏 Acknowledgments

- Spring Boot Team
- All open-source contributors
- Community feedback

---

**⭐ Nếu repo này hữu ích, đừng quên star nhé!**

Có câu hỏi? Mở issue hoặc discussion trên GitHub!
