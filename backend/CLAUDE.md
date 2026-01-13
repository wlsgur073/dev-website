# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the backend directory of the dev-website monorepo. The project is in early setup phase.

## Repository Structure

```
dev-website/
├── frontend/    # Frontend application
├── backend/     # Backend services (this directory)
└── docs/        # Documentation (Obsidian vault)
```

## MCP Servers

The project has MCP servers configured in `.vscode/mcp.json`:
- Context7 (@upstash/context7-mcp) - Context management
- Playwright (@playwright/mcp) - Browser automation and testing
- GitHub MCP Server - GitHub integration (requires Docker)
