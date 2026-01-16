-- V3__alter_api_keys_prefix.sql
-- Increase key_prefix column size to accommodate "sk_xxxx..." format

ALTER TABLE api_keys ALTER COLUMN key_prefix TYPE VARCHAR(20);
