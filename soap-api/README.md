# SOAP Web Service Module

## Giới thiệu
Module SOAP API demo SOAP Web Service với Spring WS:
- XSD schema definition
- Auto-generated WSDL
- SOAP endpoints
- JAXB data binding

## Cách chạy

```bash
cd soap-api
mvn clean compile  # Generate classes from XSD
mvn spring-boot:run
```

## Endpoints

- WSDL: http://localhost:8087/ws/users.wsdl
- SOAP Endpoint: http://localhost:8087/ws

## Test với cURL

### Get User by ID
```bash
curl -X POST http://localhost:8087/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:usr="http://lcaohoanq.com/soapapi/users">
   <soapenv:Header/>
   <soapenv:Body>
      <usr:getUserRequest>
         <usr:id>1</usr:id>
      </usr:getUserRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### Get All Users
```bash
curl -X POST http://localhost:8087/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:usr="http://lcaohoanq.com/soapapi/users">
   <soapenv:Header/>
   <soapenv:Body>
      <usr:getAllUsersRequest/>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Test với SoapUI

1. Download SoapUI
2. Create New SOAP Project
3. Import WSDL: http://localhost:8087/ws/users.wsdl
4. Test operations: getUser, getAllUsers

## SOAP vs REST

| Feature | SOAP | REST |
|---------|------|------|
| Protocol | XML-based protocol | Architecture style |
| Message format | Only XML | JSON, XML, etc. |
| Standards | WS-Security, WS-* | No strict standards |
| WSDL | Yes | No (OpenAPI optional) |
| Complexity | High | Low |
| Enterprise use | Common | Very common |
