# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a distributed Android application for CMU 95-702 Project 4 - a Tavily Search app with MVVM + Clean Architecture. The system consists of:
- **Android App** (App/) - Native Android client using MVVM pattern
- **Web Service** - Java Servlet backend (to be deployed to GitHub Codespaces)
- **MongoDB Atlas** - Cloud database for logging and analytics
- **Dashboard** - JSP-based operations dashboard

## Build Commands

### Android App
```bash
# Build debug APK
cd App && ./gradlew assembleDebug

# Build release APK
cd App && ./gradlew assembleRelease

# Run tests
cd App && ./gradlew test

# Clean build
cd App && ./gradlew clean build
```

### Web Service (when created)
```bash
# Build WAR file
mvn clean package

# Creates ROOT.war for deployment to Codespaces
```

## Architecture

### Android App (MVVM + Clean Architecture)
```
app/src/main/java/edu/cmu/tangyiq/app/
├── presentation/     # View, ViewModel, UI state
├── domain/           # Models, repository interfaces, use cases
└── data/             # Repository impl, remote data source, DTOs
```

**Data flow:** MainActivity → ViewModel → UseCase → Repository → RemoteDataSource → HTTP

### Web Service Structure
```
servlet/     # HTTP layer (SearchServlet, DashboardServlet)
service/     # Business logic
repository/  # MongoDB data access
client/      # Tavily API client
model/       # Data models
```

## Key Dependencies

- **Android**: ViewModel, LiveData, Gson
- **Web Service**: Servlet API, JSP, MongoDB Driver 4.3.4, Gson

## API

- `GET /search?query=xxx` - Search endpoint for Android
- `GET /dashboard` - Operations dashboard (web browser)

## MongoDB Logging

Logs 6+ fields per request: timestamp, query, deviceModel, tavilyLatency, resultCount, status

## Deployment

1. Build ROOT.war from web service
2. Push to GitHub Codespaces repository
3. Set port 8080 visibility to **Public**
4. Update Android app with Codespace URL

## Configuration

- Package namespace: `edu.cmu.tangyiq.app`
- Min SDK: 24, Target SDK: 35
- Java 11

## Rules
- 关于注释： 只在核心组件部分写注释，其余地方一律不写注释
- 关于写测试用例： 对于每个功能，最多只写一个测试，并且测试文件一定要写在新建的testing文件夹内
