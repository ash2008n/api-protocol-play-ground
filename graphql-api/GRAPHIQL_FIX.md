# GraphiQL Access Issue - FIXED ✅

## Vấn đề
Ban đầu khi truy cập `/graphiql`, bạn gặp lỗi CORS:
```
Access to script at 'https://unpkg.com/@graphiql/plugin-explorer@5.1.1/dist/index.umd.js' 
from origin 'http://localhost:8082' has been blocked by CORS policy
```

## Nguyên nhân
Spring Boot 3.x built-in GraphiQL UI cố load external scripts từ unpkg.com, nhưng có thể gặp:
- CORS policy restrictions
- Network/firewall blocks
- CDN unavailability
- Browser security settings

## Giải pháp đã áp dụng

### 1. Tắt GraphiQL built-in
```properties
# application.properties
spring.graphql.graphiql.enabled=false
```

### 2. Tạo custom GraphiQL HTML page
Tạo file `/static/graphiql.html` với GraphiQL CDN version mới nhất và ổn định hơn.

### 3. URLs mới

| Cũ (Lỗi) | Mới (OK) |
|-----------|----------|
| ❌ `/graphiql` | ✅ `/graphiql.html` |
| ❌ `/graphiql?path=/graphql` | ✅ `/graphiql.html` |

## Cách sử dụng

### Truy cập GraphiQL UI
```
http://localhost:8082/graphiql.html
```

### Hoặc home page
```
http://localhost:8082
```

### Test GraphQL endpoint trực tiếp
```bash
curl -X POST http://localhost:8082/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ users { id name email } }"}'
```

## Lợi ích của cách làm mới

1. ✅ **Không còn lỗi CORS** - tất cả resources đều từ CDN đáng tin cậy
2. ✅ **UI đẹp hơn** - GraphiQL 3.x với features mới
3. ✅ **Tùy biến được** - có thể customize HTML theo ý muốn
4. ✅ **Fallback** - nếu CDN down, có thể tải về local
5. ✅ **Welcome page** - có trang chủ hướng dẫn đẹp

## Alternative: Sử dụng GraphQL Playground (tuỳ chọn)

Nếu muốn dùng GraphQL Playground thay vì GraphiQL:

1. Thêm dependency:
```xml
<dependency>
    <groupId>com.graphql-java-kickstart</groupId>
    <artifactId>playground-spring-boot-starter</artifactId>
    <version>11.1.0</version>
</dependency>
```

2. Access: `http://localhost:8082/playground`

## Troubleshooting

### Vẫn không load được?

1. **Kiểm tra network**: Đảm bảo có internet để load CDN
2. **Clear browser cache**: Ctrl+Shift+Delete
3. **Thử browser khác**: Chrome, Firefox, Edge
4. **Kiểm tra firewall**: Cho phép unpkg.com
5. **Download local**: Tải GraphiQL về folder static/

### Sử dụng GraphiQL local (không cần internet)

Tải các files sau về `/static/`:
- https://unpkg.com/graphiql@3.0.10/graphiql.min.css
- https://unpkg.com/graphiql@3.0.10/graphiql.min.js
- https://unpkg.com/react@18/umd/react.production.min.js
- https://unpkg.com/react-dom@18/umd/react-dom.production.min.js

Sau đó update HTML để dùng local files.

## Summary

**TLDR**: Dùng `http://localhost:8082/graphiql.html` thay vì `/graphiql` để tránh lỗi CORS!
