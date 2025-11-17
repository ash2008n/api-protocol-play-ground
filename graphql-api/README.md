# GraphQL API Module

## Giới thiệu
Module GraphQL API demo cách sử dụng GraphQL trong Spring Boot:
- Schema-first approach với file .graphqls
- Query và Mutation operations
- DataLoader và BatchMapping để chống N+1 problem
- Nested resolvers
- GraphiQL web interface

## Công nghệ sử dụng
- Spring Boot 3.4.0
- Spring for GraphQL
- Spring Data JPA
- H2 Database
- GraphiQL UI

## Cách chạy

### 1. Build và chạy
```bash
cd graphql-api
mvn clean install
mvn spring-boot:run
```

### 2. Hoặc từ root project
```bash
mvn clean install -pl graphql-api -am
mvn spring-boot:run -pl graphql-api
```

## Endpoints

- **Home Page**: http://localhost:8082
- **GraphiQL UI**: http://localhost:8082/graphiql.html (Interactive playground)
- **GraphQL Endpoint**: http://localhost:8082/graphql (POST queries here)
- **H2 Console**: http://localhost:8082/h2-console

> **Lưu ý**: Truy cập http://localhost:8082/graphiql.html thay vì /graphiql để tránh lỗi CORS

## GraphQL Queries & Mutations

### Queries

#### 1. Get All Users
```graphql
query {
  users {
    id
    name
    email
    bio
    posts {
      id
      title
      content
    }
  }
}
```

#### 2. Get User by ID
```graphql
query {
  user(id: 1) {
    id
    name
    email
    bio
    posts {
      id
      title
    }
  }
}
```

#### 3. Get All Posts
```graphql
query {
  posts {
    id
    title
    content
    userId
    author {
      id
      name
      email
    }
  }
}
```

#### 4. Get Posts by User ID
```graphql
query {
  postsByUserId(userId: 1) {
    id
    title
    content
  }
}
```

### Mutations

#### 1. Create User
```graphql
mutation {
  createUser(input: {
    name: "John Doe"
    email: "john@example.com"
    bio: "Software Engineer"
  }) {
    id
    name
    email
    bio
  }
}
```

#### 2. Update User
```graphql
mutation {
  updateUser(id: 1, input: {
    name: "Jane Doe"
    email: "jane@example.com"
    bio: "Senior Engineer"
  }) {
    id
    name
    email
    bio
  }
}
```

#### 3. Delete User
```graphql
mutation {
  deleteUser(id: 1)
}
```

#### 4. Create Post
```graphql
mutation {
  createPost(input: {
    title: "GraphQL Tutorial"
    content: "Learn GraphQL with Spring Boot"
    userId: 1
  }) {
    id
    title
    content
    author {
      name
    }
  }
}
```

## Test với cURL

### Query Example
```bash
curl -X POST http://localhost:8082/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "{ users { id name email } }"
  }'
```

### Mutation Example
```bash
curl -X POST http://localhost:8082/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { createUser(input: { name: \"John\", email: \"john@example.com\", bio: \"Dev\" }) { id name email } }"
  }'
```

### Complex Query with Variables
```bash
curl -X POST http://localhost:8082/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query GetUser($id: ID!) { user(id: $id) { id name email posts { title } } }",
    "variables": {"id": "1"}
  }'
```

## N+1 Problem Solution

### Vấn đề N+1
Khi query nhiều users và posts của họ:
```graphql
query {
  users {
    id
    name
    posts {
      title
    }
  }
}
```

Không có DataLoader → Sẽ có N+1 queries:
- 1 query lấy tất cả users
- N queries lấy posts cho mỗi user

### Giải pháp với DataLoader và BatchMapping

Module này sử dụng 2 cách:

1. **BatchMapping** - trong `GraphQLController.posts()`:
```java
@BatchMapping(typeName = "User", field = "posts")
public Map<User, List<Post>> posts(List<User> users) {
    // Batch load posts cho tất cả users cùng lúc
    // Chỉ 1 query thay vì N queries
}
```

2. **DataLoader** - trong `DataLoaderConfig`:
```java
registry.forTypePair(Long.class, User.class)
    .registerMappedBatchLoader((userIds, environment) -> {
        // Batch load users theo IDs
        List<User> users = userRepository.findAllById(userIds);
        // Return Map<Long, User>
    });
```

## Tính năng chính

### 1. Schema-First Approach
- Define schema trong `schema.graphqls`
- Type-safe với strong typing
- Clear API contract

### 2. Flexible Queries
- Client quyết định fields cần lấy
- Nested queries
- Alias và fragments support

### 3. Performance Optimization
- DataLoader batch loading
- BatchMapping cho relationships
- Chống N+1 problem

### 4. GraphiQL Interface
- Interactive query editor
- Documentation explorer
- Schema introspection

## Ví dụ Query nâng cao

### With Aliases
```graphql
query {
  admin: user(id: 1) {
    id
    name
  }
  regularUser: user(id: 2) {
    id
    name
  }
}
```

### With Fragments
```graphql
fragment UserDetails on User {
  id
  name
  email
  bio
}

query {
  users {
    ...UserDetails
    posts {
      title
    }
  }
}
```

### Nested Query
```graphql
query {
  posts {
    id
    title
    author {
      name
      posts {
        title
      }
    }
  }
}
```

## Cấu trúc code

```
graphql-api/
├── config/
│   └── DataLoaderConfig.java       # DataLoader configuration
├── controller/
│   └── GraphQLController.java      # Query & Mutation resolvers
├── dto/
│   ├── CreateUserInput.java
│   ├── UpdateUserInput.java
│   └── CreatePostInput.java
├── entity/
│   ├── User.java
│   └── Post.java
├── repository/
│   ├── UserRepository.java
│   └── PostRepository.java
└── resources/
    └── graphql/
        └── schema.graphqls          # GraphQL schema definition
```

## So sánh với REST API

| Feature | REST | GraphQL |
|---------|------|---------|
| Data fetching | Multiple endpoints | Single endpoint |
| Over-fetching | Common | Eliminated |
| Under-fetching | Need multiple requests | One request |
| Versioning | URL versioning | Schema evolution |
| Documentation | Manual (Swagger) | Auto-generated |
| Type system | No (with OpenAPI) | Strong typing |
