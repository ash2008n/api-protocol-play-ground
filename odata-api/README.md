# OData API Module

Demo of OData v4 RESTful API with Apache Olingo, providing standardized query capabilities for data access.

## Features

- **OData v4 Protocol**: RESTful API with OData conventions
- **Apache Olingo**: OData v4 server implementation
- **Entity Data Model (EDM)**: Metadata-driven API
- **$filter, $select, $orderby**: OData query options
- **JSON Format**: Standard OData JSON responses
- **Service Document**: Auto-generated metadata
- **H2 Database**: In-memory data storage with sample products

## Port

- HTTP Server: `8090`
- OData Service: `/odata.svc`

## Running the Application

```bash
cd odata-api
mvn spring-boot:run
```

## Access Points

- **Service Root**: http://localhost:8090/odata.svc/
- **Metadata**: http://localhost:8090/odata.svc/$metadata
- **Products Collection**: http://localhost:8090/odata.svc/Products
- **H2 Console**: http://localhost:8090/h2-console
  - JDBC URL: `jdbc:h2:mem:odatadb`
  - Username: `sa`
  - Password: (empty)

## OData Queries

### 1. Get All Products
```bash
curl http://localhost:8090/odata.svc/Products
```

### 2. Get Single Product by ID
```bash
curl http://localhost:8090/odata.svc/Products(1)
```

### 3. Filter Products
```bash
# Products with price less than 100
curl "http://localhost:8090/odata.svc/Products?\$filter=Price lt 100"

# Products in Electronics category
curl "http://localhost:8090/odata.svc/Products?\$filter=Category eq 'Electronics'"

# Products with price between 50 and 300
curl "http://localhost:8090/odata.svc/Products?\$filter=Price ge 50 and Price le 300"
```

### 4. Select Specific Properties
```bash
# Only Name and Price
curl "http://localhost:8090/odata.svc/Products?\$select=Name,Price"
```

### 5. Order Results
```bash
# Order by Price ascending
curl "http://localhost:8090/odata.svc/Products?\$orderby=Price"

# Order by Price descending
curl "http://localhost:8090/odata.svc/Products?\$orderby=Price desc"
```

### 6. Pagination
```bash
# Top 3 products
curl "http://localhost:8090/odata.svc/Products?\$top=3"

# Skip first 2 products
curl "http://localhost:8090/odata.svc/Products?\$skip=2"
```

### 7. Complex Queries (Combine Options)
```bash
# Electronics under $100, ordered by Price, only Name and Price
curl "http://localhost:8090/odata.svc/Products?\$filter=Category eq 'Electronics' and Price lt 100&\$select=Name,Price&\$orderby=Price"
```

### 8. Count
```bash
# Get total count
curl "http://localhost:8090/odata.svc/Products?\$count=true"
```

## OData Query Options

| Option | Description | Example |
|--------|-------------|---------|
| `$filter` | Filter results | `Price gt 50` |
| `$select` | Choose properties | `Name,Price` |
| `$orderby` | Sort results | `Price desc` |
| `$top` | Limit results | `5` |
| `$skip` | Skip results | `10` |
| `$count` | Include count | `true` |
| `$expand` | Expand related | `Category` |

## Filter Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `eq` | Equal | `Category eq 'Electronics'` |
| `ne` | Not equal | `Price ne 100` |
| `gt` | Greater than | `Price gt 50` |
| `ge` | Greater or equal | `Quantity ge 100` |
| `lt` | Less than | `Price lt 200` |
| `le` | Less or equal | `Quantity le 50` |
| `and` | Logical AND | `Price gt 50 and Price lt 100` |
| `or` | Logical OR | `Category eq 'Books' or Category eq 'Furniture'` |
| `not` | Logical NOT | `not (Price gt 1000)` |

## Response Format

### Service Document
```json
{
  "@odata.context": "$metadata",
  "value": [
    {
      "name": "Products",
      "kind": "EntitySet",
      "url": "Products"
    }
  ]
}
```

### Entity Collection
```json
{
  "@odata.context": "$metadata#Products",
  "value": [
    {
      "Id": 1,
      "Name": "Laptop",
      "Description": "High-performance laptop",
      "Price": 1299.99,
      "Quantity": 50,
      "Category": "Electronics"
    }
  ]
}
```

### Single Entity
```json
{
  "@odata.context": "$metadata#Products/$entity",
  "Id": 1,
  "Name": "Laptop",
  "Description": "High-performance laptop",
  "Price": 1299.99,
  "Quantity": 50,
  "Category": "Electronics"
}
```

## Sample Data

The application initializes with 5 sample products:

1. **Laptop** - Electronics, $1299.99
2. **Mouse** - Electronics, $29.99
3. **Keyboard** - Electronics, $89.99
4. **Book - Java Programming** - Books, $49.99
5. **Office Chair** - Furniture, $299.99

## OData Architecture

```
Client Request
     ↓
OData Servlet (/odata.svc/*)
     ↓
OData HTTP Handler
     ↓
EDM Provider (Metadata)
     ↓
Entity Processor
     ↓
Service Layer
     ↓
Database (H2)
```

## Key Concepts

- **EDM (Entity Data Model)**: Describes data structure
- **Entity Type**: Like a class/table definition
- **Entity Set**: Collection of entities
- **Service Metadata**: `$metadata` endpoint
- **OData Conventions**: Standardized URL patterns
- **Query Options**: `$filter`, `$select`, `$orderby`, etc.

## Benefits of OData

1. **Standardization**: Industry-standard protocol
2. **Flexibility**: Rich querying without custom endpoints
3. **Discoverability**: Self-documenting via metadata
4. **Efficiency**: Client controls data shape
5. **Interoperability**: Works with any OData client

## Testing with Tools

### Browser
Navigate to: http://localhost:8090/odata.svc/Products

### Postman
Import OData queries as GET requests

### Excel/PowerBI
Connect to OData feed directly

### OData Clients
- Simple.OData.Client (.NET)
- Apache Olingo (Java)
- JayData (JavaScript)

## Limitations (Demo Version)

This demo implements READ operations only:
- ✅ GET (ReadEntityCollection, ReadEntity)
- ❌ POST (Create) - Not implemented
- ❌ PATCH/PUT (Update) - Not implemented
- ❌ DELETE - Not implemented

## Technologies

- Spring Boot 3.4.0
- Apache Olingo 4.10.0
- OData v4
- H2 Database
- Lombok

## OData Resources

- **OData Specification**: https://www.odata.org/documentation/
- **Apache Olingo**: https://olingo.apache.org/
- **OData Tutorial**: https://www.odata.org/getting-started/

## Common Issues

**Empty response?**
- Ensure H2 database is populated (check logs)
- Verify URL: `/odata.svc/Products` (case-sensitive)

**Metadata not loading?**
- Check: http://localhost:8090/odata.svc/$metadata
- Verify Apache Olingo dependencies

**Query not working?**
- URL-encode special characters
- Use `\$` in bash/curl for `$` character
- Check OData syntax (spaces, quotes)
