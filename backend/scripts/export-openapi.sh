#!/bin/bash

# Export OpenAPI JSON from running server
# Usage: ./scripts/export-openapi.sh

set -e

SERVER_URL="${SERVER_URL:-http://localhost:8080}"
OUTPUT_FILE="openapi.json"

echo "Fetching OpenAPI spec from $SERVER_URL/v3/api-docs..."

curl -s "$SERVER_URL/v3/api-docs" -o "$OUTPUT_FILE"

if [ -f "$OUTPUT_FILE" ] && [ -s "$OUTPUT_FILE" ]; then
    echo "OpenAPI spec saved to $OUTPUT_FILE"
else
    echo "Error: Failed to fetch OpenAPI spec"
    exit 1
fi
