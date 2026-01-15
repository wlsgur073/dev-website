# Error Handling

Guide to handling API errors and troubleshooting.

## Error Response Format

All errors follow a consistent format:

```json
{
  "error": {
    "code": "error_code",
    "message": "Human-readable error message",
    "details": {
      "field": "Additional context"
    }
  }
}
```

## HTTP Status Codes

### Success Codes

| Code | Description |
|------|-------------|
| 200 | OK - Request succeeded |
| 201 | Created - Resource created successfully |
| 204 | No Content - Request succeeded, no response body |

### Client Error Codes

| Code | Description |
|------|-------------|
| 400 | Bad Request - Invalid request format or parameters |
| 401 | Unauthorized - Missing or invalid authentication |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource doesn't exist |
| 409 | Conflict - Resource conflict (e.g., duplicate) |
| 422 | Unprocessable Entity - Validation error |
| 429 | Too Many Requests - Rate limit exceeded |

### Server Error Codes

| Code | Description |
|------|-------------|
| 500 | Internal Server Error - Unexpected server error |
| 502 | Bad Gateway - Upstream service error |
| 503 | Service Unavailable - Temporary maintenance |
| 504 | Gateway Timeout - Request timeout |

## Common Errors

### Authentication Errors

#### invalid_api_key

```json
{
  "error": {
    "code": "invalid_api_key",
    "message": "The provided API key is invalid or has been revoked"
  }
}
```

**Solution:** Check that your API key is correct and active in the Console.

#### missing_authorization

```json
{
  "error": {
    "code": "missing_authorization",
    "message": "Authorization header is required"
  }
}
```

**Solution:** Include the `Authorization: Bearer YOUR_API_KEY` header.

### Validation Errors

#### validation_error

```json
{
  "error": {
    "code": "validation_error",
    "message": "Request validation failed",
    "details": {
      "name": "Name is required",
      "email": "Invalid email format"
    }
  }
}
```

**Solution:** Check the `details` object for specific field errors.

### Resource Errors

#### not_found

```json
{
  "error": {
    "code": "not_found",
    "message": "Resource with ID 'res_123' not found"
  }
}
```

**Solution:** Verify the resource ID exists and you have access to it.

#### conflict

```json
{
  "error": {
    "code": "conflict",
    "message": "A resource with this name already exists"
  }
}
```

**Solution:** Use a unique identifier or update the existing resource.

## Error Handling Examples

### TypeScript

```typescript
async function apiRequest<T>(url: string, options?: RequestInit): Promise<T> {
  const response = await fetch(url, {
    ...options,
    headers: {
      'Authorization': `Bearer ${API_KEY}`,
      'Content-Type': 'application/json',
      ...options?.headers,
    },
  });

  if (!response.ok) {
    const error = await response.json();

    switch (response.status) {
      case 401:
        throw new AuthenticationError(error.error.message);
      case 403:
        throw new ForbiddenError(error.error.message);
      case 404:
        throw new NotFoundError(error.error.message);
      case 422:
        throw new ValidationError(error.error.message, error.error.details);
      case 429:
        throw new RateLimitError(error.error.message);
      default:
        throw new ApiError(error.error.message, response.status);
    }
  }

  return response.json();
}

// Usage with try-catch
try {
  const data = await apiRequest('/resources');
} catch (error) {
  if (error instanceof ValidationError) {
    console.error('Validation failed:', error.details);
  } else if (error instanceof RateLimitError) {
    console.error('Rate limited, retry later');
  } else {
    console.error('API error:', error.message);
  }
}
```

### Python

```python
import requests

class ApiError(Exception):
    def __init__(self, message, status_code, details=None):
        self.message = message
        self.status_code = status_code
        self.details = details or {}
        super().__init__(message)

def api_request(url, method='GET', data=None):
    response = requests.request(
        method,
        f'https://api.devwebsite.com/v1{url}',
        headers={'Authorization': f'Bearer {API_KEY}'},
        json=data
    )

    if not response.ok:
        error = response.json().get('error', {})
        raise ApiError(
            error.get('message', 'Unknown error'),
            response.status_code,
            error.get('details')
        )

    return response.json()

# Usage
try:
    data = api_request('/resources')
except ApiError as e:
    if e.status_code == 422:
        print(f'Validation failed: {e.details}')
    elif e.status_code == 429:
        print('Rate limited, retry later')
    else:
        print(f'API error: {e.message}')
```

## Troubleshooting

### Debug Mode

Enable debug mode to see detailed request/response info:

```bash
curl -v "https://api.devwebsite.com/v1/resources" \
  -H "Authorization: Bearer YOUR_API_KEY"
```

### Request ID

Every response includes a `X-Request-ID` header. Include this when contacting support:

```http
X-Request-ID: req_abc123xyz
```

## Getting Help

If you encounter persistent errors:

1. Check the [API Status Page](https://status.devwebsite.com)
2. Search the [FAQ](/docs/faq)
3. Contact support with your Request ID

## Next Steps

- Review [Rate Limits](/docs/rate-limits)
- Check [API Usage](/docs/api-usage) examples
- Read the [FAQ](/docs/faq)
