# Rate Limits

Understanding and handling API rate limits.

## Overview

The Dev Website API implements rate limiting to ensure fair usage and maintain service stability. Rate limits vary by plan and endpoint.

## Rate Limit Tiers

| Plan | Requests/Minute | Requests/Day |
|------|-----------------|--------------|
| Free | 60 | 1,000 |
| Starter | 300 | 10,000 |
| Pro | 1,000 | 100,000 |
| Enterprise | Custom | Custom |

## Rate Limit Headers

Every API response includes rate limit information in the headers:

```http
X-RateLimit-Limit: 60
X-RateLimit-Remaining: 45
X-RateLimit-Reset: 1705312800
```

| Header | Description |
|--------|-------------|
| `X-RateLimit-Limit` | Maximum requests allowed in the window |
| `X-RateLimit-Remaining` | Requests remaining in current window |
| `X-RateLimit-Reset` | Unix timestamp when the window resets |

## Handling Rate Limits

### 429 Too Many Requests

When you exceed the rate limit, you'll receive:

```json
{
  "error": {
    "code": "rate_limit_exceeded",
    "message": "Too many requests. Please retry after 30 seconds.",
    "retryAfter": 30
  }
}
```

### Retry Strategy

Implement exponential backoff:

```typescript
async function fetchWithRetry(url: string, maxRetries = 3): Promise<Response> {
  for (let attempt = 0; attempt < maxRetries; attempt++) {
    const response = await fetch(url, {
      headers: { 'Authorization': `Bearer ${API_KEY}` }
    });

    if (response.status === 429) {
      const retryAfter = parseInt(response.headers.get('Retry-After') || '30');
      const delay = retryAfter * 1000 * Math.pow(2, attempt);
      console.log(`Rate limited. Retrying in ${delay}ms...`);
      await new Promise(resolve => setTimeout(resolve, delay));
      continue;
    }

    return response;
  }

  throw new Error('Max retries exceeded');
}
```

### Python Example

```python
import time
import requests

def fetch_with_retry(url, max_retries=3):
    for attempt in range(max_retries):
        response = requests.get(url, headers={'Authorization': f'Bearer {API_KEY}'})

        if response.status_code == 429:
            retry_after = int(response.headers.get('Retry-After', 30))
            delay = retry_after * (2 ** attempt)
            print(f'Rate limited. Retrying in {delay}s...')
            time.sleep(delay)
            continue

        return response

    raise Exception('Max retries exceeded')
```

## Best Practices

### 1. Cache Responses

Reduce API calls by caching responses:

```typescript
const cache = new Map();

async function getCachedData(key: string): Promise<Data> {
  if (cache.has(key)) {
    const { data, expiry } = cache.get(key);
    if (Date.now() < expiry) {
      return data;
    }
  }

  const data = await fetchData(key);
  cache.set(key, { data, expiry: Date.now() + 60000 }); // 1 minute cache
  return data;
}
```

### 2. Batch Requests

Use batch endpoints when available:

```bash
# Instead of multiple single requests
POST /resources/batch
{
  "ids": ["res_1", "res_2", "res_3"]
}
```

### 3. Use Webhooks

For real-time updates, use webhooks instead of polling:

```javascript
// Bad: Polling every 5 seconds
setInterval(() => checkStatus(), 5000);

// Good: Use webhooks
app.post('/webhook', (req, res) => {
  const event = req.body;
  handleStatusUpdate(event);
  res.sendStatus(200);
});
```

### 4. Monitor Usage

Track your API usage in the [Console Dashboard](/console):

- View current usage vs limits
- Set up usage alerts
- Analyze request patterns

## Requesting Higher Limits

If you need higher rate limits:

1. Review your [current plan](/pricing)
2. Consider upgrading to a higher tier
3. Contact [sales@devwebsite.com](mailto:sales@devwebsite.com) for Enterprise plans

## Next Steps

- Learn about [Error Handling](/docs/errors)
- View [API Usage](/docs/api-usage) documentation
- Check the [FAQ](/docs/faq)
