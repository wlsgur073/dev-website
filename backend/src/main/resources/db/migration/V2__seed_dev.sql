-- V2__seed_dev.sql
-- Development seed data for testing

-- Seed users (passwords are bcrypt hashed)
-- admin@example.com / admin123
-- user@example.com / user123

INSERT INTO users (email, password, nickname, role, created_at, updated_at)
VALUES
    (
        'admin@example.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqFb.EFmxh.RhFjq2j9Z2t0HMz4t6',
        'Admin',
        'ROLE_ADMIN',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'user@example.com',
        '$2a$10$8K1p/a0dL1LXMIgoEDFrwOqGGSO6LLg1IjG3OKWQY3G9H.d9lXe6G',
        'User',
        'ROLE_USER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
ON CONFLICT (email) DO NOTHING;

-- Seed plans for future Phase 5
INSERT INTO plans (name, display_name, description, price_monthly, features, created_at)
VALUES
    (
        'free',
        'Free',
        'Free tier with basic features',
        0.00,
        '{"api_calls": 1000, "storage_gb": 1, "support": "community"}',
        CURRENT_TIMESTAMP
    ),
    (
        'pro',
        'Pro',
        'Professional tier for growing teams',
        29.00,
        '{"api_calls": 50000, "storage_gb": 10, "support": "email"}',
        CURRENT_TIMESTAMP
    ),
    (
        'team',
        'Team',
        'Enterprise tier for large organizations',
        99.00,
        '{"api_calls": -1, "storage_gb": 100, "support": "priority"}',
        CURRENT_TIMESTAMP
    )
ON CONFLICT (name) DO NOTHING;
