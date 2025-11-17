# Webhook Receiver Module

## Giới thiệu
Module Webhook Receiver demo việc nhận và xử lý webhooks:
- Payment webhook với signature verification
- GitHub webhook simulation
- Stripe webhook simulation
- Webhook logging
- HMAC SHA-256 signature verification

## Cách chạy

```bash
cd webhook-receiver
mvn spring-boot:run
```

## Endpoints

- `POST /webhook/payment` - Payment webhooks
- `POST /webhook/github` - GitHub webhooks
- `POST /webhook/stripe` - Stripe webhooks
- `GET /webhook/logs` - View recent webhook logs

## Test

### 1. Payment Webhook (with valid signature)
```bash
curl -X POST http://localhost:8086/webhook/payment \
  -H "Content-Type: application/json" \
  -H "X-Webhook-Signature: valid-signature" \
  -d '{
    "eventId": "evt_123",
    "eventType": "payment.success",
    "data": "Payment for order #12345",
    "timestamp": "2024-01-01T10:00:00"
  }'
```

### 2. Payment Webhook (invalid signature)
```bash
curl -X POST http://localhost:8086/webhook/payment \
  -H "Content-Type: application/json" \
  -H "X-Webhook-Signature: invalid" \
  -d '{
    "eventId": "evt_124",
    "eventType": "payment.failed",
    "data": "Payment failed",
    "timestamp": "2024-01-01T10:01:00"
  }'
```

### 3. GitHub Webhook
```bash
curl -X POST http://localhost:8086/webhook/github \
  -H "Content-Type: application/json" \
  -H "X-GitHub-Event: push" \
  -d '{"ref": "refs/heads/main", "commits": []}'
```

### 4. View Logs
```bash
curl http://localhost:8086/webhook/logs
```

## Signature Verification

Webhooks sử dụng HMAC SHA-256 để verify authenticity:

1. Secret key: `my-secret-key` (config in application.properties)
2. Compute: `HMAC-SHA256(payload, secret)`
3. Compare với header `X-Webhook-Signature`

For demo, signature `valid-signature` luôn được accept.

## Use Cases

- **Payment Gateways**: Stripe, PayPal notifications
- **Version Control**: GitHub, GitLab events
- **CI/CD**: Build status, deployment events
- **Monitoring**: Alert notifications
- **Integration**: Third-party service updates
