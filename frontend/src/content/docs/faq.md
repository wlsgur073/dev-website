# Frequently Asked Questions

Common questions and answers about the Dev Website API.

## General Questions

### What is Dev Website?

Dev Website is a developer platform that provides powerful APIs for building modern applications. We offer tools for authentication, data management, and more.

### How do I get started?

1. [Create an account](/register)
2. Get your API key from the [Console](/console/api-keys)
3. Follow our [Quickstart Guide](/docs/quickstart)

### Is there a free tier?

Yes! Our free tier includes:
- 1,000 API requests per day
- 60 requests per minute
- Basic support via documentation

[View all plans](/pricing)

## Authentication

### How do I authenticate API requests?

Include your API key in the `Authorization` header:

```bash
Authorization: Bearer YOUR_API_KEY
```

See the [Authentication Guide](/docs/authentication) for details.

### Can I have multiple API keys?

Yes, you can create multiple API keys for different environments or services. Each key can have different permissions.

### What should I do if my API key is compromised?

1. Immediately revoke the key in the [Console](/console/api-keys)
2. Create a new API key
3. Update your applications with the new key
4. Review your logs for unauthorized access

## Rate Limits

### What are the rate limits?

Rate limits depend on your plan:

| Plan | Requests/Min | Requests/Day |
|------|--------------|--------------|
| Free | 60 | 1,000 |
| Starter | 300 | 10,000 |
| Pro | 1,000 | 100,000 |

### How do I know if I'm being rate limited?

Check the response headers:
- `X-RateLimit-Remaining`: Requests left
- `X-RateLimit-Reset`: When the limit resets

A 429 status code means you've exceeded the limit.

### Can I request higher limits?

Yes, contact our sales team for custom Enterprise plans.

## Billing

### How does billing work?

- Free tier: No charge
- Paid plans: Monthly subscription
- Usage-based charges for overages

### Can I change my plan?

Yes, you can upgrade or downgrade anytime from the [Billing page](/console/billing). Changes take effect immediately.

### Do you offer refunds?

We offer refunds within 14 days of your first payment if you're not satisfied. Contact support for assistance.

## Technical Questions

### What programming languages do you support?

We provide official SDKs for:
- JavaScript/TypeScript
- Python
- Go
- Java

Our REST API works with any language that can make HTTP requests.

### Do you support webhooks?

Yes! Configure webhooks in the Console to receive real-time event notifications.

### Is the API available in all regions?

Our API is globally distributed with endpoints in:
- North America
- Europe
- Asia Pacific

Requests are automatically routed to the nearest region.

### What's the API uptime guarantee?

- Pro plans: 99.9% uptime SLA
- Enterprise plans: 99.99% uptime SLA

Check our [Status Page](https://status.devwebsite.com) for real-time availability.

## Data & Security

### Is my data secure?

Yes, we take security seriously:
- All data encrypted in transit (TLS 1.3)
- Data encrypted at rest (AES-256)
- SOC 2 Type II certified
- GDPR compliant

### Where is my data stored?

By default, data is stored in our US data centers. Enterprise customers can choose regional data residency.

### Can I export my data?

Yes, you can export all your data from the Console or via the API. We support JSON and CSV formats.

### How do I delete my account?

1. Go to Account Settings
2. Click "Delete Account"
3. Confirm deletion

All data will be permanently deleted within 30 days.

## Support

### How do I contact support?

- **Documentation**: Start here for most questions
- **Email**: support@devwebsite.com
- **Discord**: [Join our community](https://discord.gg/devwebsite)
- **Enterprise**: Dedicated support channel

### What are support hours?

- Free tier: Community support only
- Starter: Business hours (Mon-Fri, 9am-5pm EST)
- Pro: Extended hours (Mon-Fri, 7am-10pm EST)
- Enterprise: 24/7 support

### Do you have a bug bounty program?

Yes! Report security vulnerabilities to security@devwebsite.com. Valid reports are eligible for rewards up to $5,000.

## Still Have Questions?

- Join our [Community](/community) for discussions
- Check out more [Documentation](/docs)
- Contact [Support](mailto:support@devwebsite.com)
