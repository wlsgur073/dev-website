# Authentication

Learn how to authenticate with the Dev Website API.

## Overview

The Dev Website API uses **Bearer Token Authentication**. You must include your API key in the `Authorization` header of every request.

## API Keys

### Creating an API Key

1. Navigate to [Console > API Keys](/console/api-keys)
2. Click **Create New Key**
3. Give your key a descriptive name
4. Copy and store the key securely

### Using Your API Key

Include the API key in the `Authorization` header:

```bash
Authorization: Bearer your_api_key_here
```

### Example Request

```bash
curl -X GET "https://api.devwebsite.com/v1/resources" \
  -H "Authorization: Bearer sk_live_abc123xyz" \
  -H "Content-Type: application/json"
```

## Security Best Practices

### Never Expose API Keys

- **Don't** commit API keys to version control
- **Don't** include API keys in client-side code
- **Do** use environment variables
- **Do** use server-side proxies for browser apps

### Environment Variables

```bash
# .env file (never commit this!)
API_KEY=sk_live_abc123xyz
```

```javascript
// Access in your code
const apiKey = process.env.API_KEY;
```

### Key Rotation

Regularly rotate your API keys to minimize security risks:

1. Create a new API key
2. Update your application to use the new key
3. Verify the new key works correctly
4. Delete the old API key

## Error Responses

### 401 Unauthorized

```json
{
  "error": {
    "code": "unauthorized",
    "message": "Invalid or missing API key"
  }
}
```

**Causes:**
- Missing `Authorization` header
- Invalid API key
- Expired or revoked API key

### 403 Forbidden

```json
{
  "error": {
    "code": "forbidden",
    "message": "API key does not have permission for this resource"
  }
}
```

**Causes:**
- API key lacks required permissions
- Resource belongs to a different organization

## Scopes and Permissions

API keys can have different permission levels:

| Scope | Description |
|-------|-------------|
| `read` | Read-only access to resources |
| `write` | Create and update resources |
| `delete` | Delete resources |
| `admin` | Full administrative access |

## Next Steps

- Learn about [Rate Limits](/docs/rate-limits)
- Explore the [API Reference](/docs/api-usage)
- Check the [Error Handling](/docs/errors) guide
