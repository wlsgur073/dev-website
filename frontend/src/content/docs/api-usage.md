# API Usage

Complete guide to using the Dev Website API.

## Base URL

All API requests should be made to:

```
https://api.devwebsite.com/v1
```

## Request Format

### Headers

All requests must include:

```http
Authorization: Bearer YOUR_API_KEY
Content-Type: application/json
```

### Request Body

For `POST`, `PUT`, and `PATCH` requests, send data as JSON:

```json
{
  "name": "Example Resource",
  "description": "A sample resource"
}
```

## Response Format

All responses are returned as JSON:

```json
{
  "data": {
    "id": "res_123",
    "name": "Example Resource",
    "createdAt": "2024-01-15T10:30:00Z"
  },
  "meta": {
    "requestId": "req_abc123"
  }
}
```

## Endpoints

### Resources

#### List Resources

```http
GET /resources
```

**Query Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| `page` | integer | Page number (default: 1) |
| `limit` | integer | Items per page (default: 20, max: 100) |
| `sort` | string | Sort field (`created`, `updated`, `name`) |
| `order` | string | Sort order (`asc`, `desc`) |

**Example:**

```bash
curl "https://api.devwebsite.com/v1/resources?page=1&limit=10" \
  -H "Authorization: Bearer YOUR_API_KEY"
```

**Response:**

```json
{
  "data": [
    {
      "id": "res_123",
      "name": "Resource 1",
      "createdAt": "2024-01-15T10:30:00Z"
    }
  ],
  "pagination": {
    "page": 1,
    "limit": 10,
    "total": 42,
    "totalPages": 5
  }
}
```

#### Get Resource

```http
GET /resources/:id
```

#### Create Resource

```http
POST /resources
```

**Request Body:**

```json
{
  "name": "New Resource",
  "description": "Resource description",
  "tags": ["tag1", "tag2"]
}
```

#### Update Resource

```http
PUT /resources/:id
```

#### Delete Resource

```http
DELETE /resources/:id
```

## Pagination

List endpoints support cursor-based pagination:

```json
{
  "data": [...],
  "pagination": {
    "page": 1,
    "limit": 20,
    "total": 150,
    "totalPages": 8,
    "hasMore": true
  }
}
```

## Filtering

Use query parameters to filter results:

```bash
# Filter by status
GET /resources?status=active

# Filter by date range
GET /resources?createdAfter=2024-01-01&createdBefore=2024-12-31

# Multiple filters
GET /resources?status=active&type=premium
```

## Sorting

Sort results using `sort` and `order` parameters:

```bash
# Sort by creation date, newest first
GET /resources?sort=created&order=desc

# Sort by name, alphabetically
GET /resources?sort=name&order=asc
```

## SDK Examples

### JavaScript/TypeScript

```typescript
import { DevWebsiteClient } from '@devwebsite/sdk';

const client = new DevWebsiteClient({
  apiKey: process.env.API_KEY,
});

// List resources
const resources = await client.resources.list({
  page: 1,
  limit: 20,
});

// Get a single resource
const resource = await client.resources.get('res_123');

// Create a resource
const newResource = await client.resources.create({
  name: 'My Resource',
  description: 'Description here',
});
```

### Python

```python
from devwebsite import Client

client = Client(api_key=os.environ['API_KEY'])

# List resources
resources = client.resources.list(page=1, limit=20)

# Get a single resource
resource = client.resources.get('res_123')

# Create a resource
new_resource = client.resources.create(
    name='My Resource',
    description='Description here'
)
```

## Next Steps

- Review [Error Handling](/docs/errors)
- Understand [Rate Limits](/docs/rate-limits)
- Check the [FAQ](/docs/faq)
