# Quickstart Guide

Get started with Dev Website API in just a few minutes.

## Prerequisites

Before you begin, make sure you have:

- A Dev Website account ([Sign up here](/register))
- Basic knowledge of REST APIs
- A tool for making HTTP requests (curl, Postman, or your favorite programming language)

## Step 1: Get Your API Key

1. Log in to your [Console](/console)
2. Navigate to **API Keys** section
3. Click **Create New Key**
4. Copy and securely store your API key

> **Important**: Your API key will only be shown once. Store it securely and never expose it in client-side code.

## Step 2: Make Your First Request

Test your API key with a simple request:

```bash
curl -X GET "https://api.devwebsite.com/v1/status" \
  -H "Authorization: Bearer YOUR_API_KEY"
```

Expected response:

```json
{
  "status": "ok",
  "timestamp": "2024-01-15T10:30:00Z",
  "version": "1.0.0"
}
```

## Step 3: Explore the API

Now that you're authenticated, you can start using the full API:

### JavaScript/TypeScript

```typescript
const response = await fetch('https://api.devwebsite.com/v1/data', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${process.env.API_KEY}`,
    'Content-Type': 'application/json',
  },
});

const data = await response.json();
console.log(data);
```

### Python

```python
import requests

headers = {
    'Authorization': f'Bearer {API_KEY}',
    'Content-Type': 'application/json',
}

response = requests.get('https://api.devwebsite.com/v1/data', headers=headers)
data = response.json()
print(data)
```

## Next Steps

- Read the [Authentication](/docs/authentication) guide for advanced auth options
- Check out the [API Usage](/docs/api-usage) documentation
- Explore [Rate Limits](/docs/rate-limits) to understand usage limits
- Join our [Community](/community) for support and discussions
