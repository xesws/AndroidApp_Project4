# Task 2 Development Plan
## MVVM + Clean Architecture

**Author:** Tangyi Qian
**Andrew ID:** tangyiq
**Date:** November 22, 2024

---

## Project Overview

**Application:** Tavily Search - AI-powered search application
**API:** Tavily Search API
**Database:** MongoDB Atlas

---

## System Architecture

```
┌─────────────┐     HTTP/JSON     ┌─────────────┐     HTTP/JSON     ┌─────────────┐
│   Android   │ ◄──────────────► │ Web Service │ ◄──────────────► │ Tavily API  │
│     App     │                   │  (Servlet)  │                   │             │
└─────────────┘                   └──────┬──────┘                   └─────────────┘
                                         │
                                         │ CRUD
                                         ▼
                                  ┌─────────────┐
                                  │  MongoDB    │
                                  │   Atlas     │
                                  └──────┬──────┘
                                         │
                                         ▼
                                  ┌─────────────┐
                                  │  Dashboard  │
                                  │    (JSP)    │
                                  └─────────────┘
```

---

## Phase 1: Android App (MVVM + Clean Architecture)

### 1.1 Project Structure

```
app/src/main/java/edu/cmu/tangyiq/app/
│
├── presentation/                    # Presentation Layer
│   ├── MainActivity.java            # View - UI only
│   ├── MainViewModel.java           # ViewModel - UI state management
│   ├── MainViewModelFactory.java    # ViewModel factory
│   └── SearchState.java             # UI state wrapper (Loading/Success/Error)
│
├── domain/                          # Domain Layer
│   ├── model/
│   │   └── SearchResult.java        # Domain model
│   ├── repository/
│   │   └── SearchRepository.java    # Repository interface
│   └── usecase/
│       └── SearchUseCase.java       # Business use case
│
└── data/                            # Data Layer
    ├── repository/
    │   └── SearchRepositoryImpl.java # Repository implementation
    ├── remote/
    │   └── RemoteDataSource.java     # Remote data source (HTTP)
    └── dto/
        ├── ApiResponse.java          # API response DTO
        └── SearchResultDto.java      # Search result DTO
```

### 1.2 Tasks

- [ ] **1.2.1** Add ViewModel/LiveData dependencies to build.gradle.kts
- [ ] **1.2.2** Create Domain Layer
  - [ ] `domain/model/SearchResult.java`
  - [ ] `domain/repository/SearchRepository.java`
  - [ ] `domain/usecase/SearchUseCase.java`
- [ ] **1.2.3** Create Data Layer
  - [ ] `data/dto/SearchResultDto.java`
  - [ ] `data/dto/ApiResponse.java`
  - [ ] `data/remote/RemoteDataSource.java`
  - [ ] `data/repository/SearchRepositoryImpl.java`
- [ ] **1.2.4** Create Presentation Layer
  - [ ] `presentation/SearchState.java`
  - [ ] `presentation/MainViewModel.java`
  - [ ] `presentation/MainViewModelFactory.java`
  - [ ] Refactor `presentation/MainActivity.java`

### 1.3 Key Classes Design

#### SearchState.java
```java
public class SearchState {
    public enum Status { IDLE, LOADING, SUCCESS, ERROR }

    private Status status;
    private List<SearchResult> results;
    private String errorMessage;

    // Static factory methods
    public static SearchState idle() { ... }
    public static SearchState loading() { ... }
    public static SearchState success(List<SearchResult> results) { ... }
    public static SearchState error(String message) { ... }
}
```

#### SearchResult.java (Domain Model)
```java
public class SearchResult {
    private String title;
    private String url;
    private String snippet;

    // Constructor, getters
}
```

#### SearchRepository.java (Interface)
```java
public interface SearchRepository {
    void search(String query, RepositoryCallback<List<SearchResult>> callback);
}

public interface RepositoryCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}
```

### 1.4 Dependencies

```kotlin
// build.gradle.kts
dependencies {
    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")

    // JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")
}
```

---

## Phase 2: Web Service

### 2.1 Project Structure

```
WebService/src/main/java/edu/cmu/tangyiq/
│
├── servlet/                         # HTTP Layer
│   ├── SearchServlet.java           # Handle search requests
│   └── DashboardServlet.java        # Handle dashboard requests
│
├── service/                         # Business Logic Layer
│   ├── SearchService.java           # Search business logic
│   └── AnalyticsService.java        # Analytics business logic
│
├── repository/                      # Data Access Layer
│   └── LogRepository.java           # Log storage (MongoDB)
│
├── client/                          # External API Client
│   └── TavilyClient.java            # Tavily API calls
│
├── model/                           # Data Models
│   ├── SearchLog.java               # Log entity
│   └── AnalyticsData.java           # Analytics data
│
└── util/
    └── JsonUtil.java                # JSON utilities

WebService/src/main/webapp/
├── WEB-INF/
│   └── web.xml                      # Servlet configuration
└── dashboard.jsp                    # Dashboard view
```

### 2.2 Tasks

- [ ] **2.2.1** Create Maven Web Project
- [ ] **2.2.2** Configure pom.xml with dependencies
- [ ] **2.2.3** Implement Client Layer
  - [ ] `client/TavilyClient.java`
- [ ] **2.2.4** Implement Repository Layer
  - [ ] `repository/LogRepository.java`
- [ ] **2.2.5** Implement Service Layer
  - [ ] `service/SearchService.java`
  - [ ] `service/AnalyticsService.java`
- [ ] **2.2.6** Implement Servlet Layer
  - [ ] `servlet/SearchServlet.java`
  - [ ] `servlet/DashboardServlet.java`
- [ ] **2.2.7** Create Models
  - [ ] `model/SearchLog.java`
  - [ ] `model/AnalyticsData.java`
- [ ] **2.2.8** Create Dashboard JSP
- [ ] **2.2.9** Configure web.xml

### 2.3 MongoDB Logging (6+ fields)

| # | Field | Description |
|---|-------|-------------|
| 1 | timestamp | Request timestamp |
| 2 | query | User search query |
| 3 | deviceModel | Device model (from User-Agent) |
| 4 | tavilyLatency | Tavily API response time (ms) |
| 5 | resultCount | Number of results returned |
| 6 | status | Success/Error status |
| 7 | clientIP | Client IP address (optional) |

### 2.4 Dashboard Analytics (3+ metrics)

1. **Top 10 Search Queries** - Most frequently searched terms
2. **Average API Latency** - Average Tavily API response time
3. **Total Search Count** - Cumulative number of searches

### 2.5 Dependencies

```xml
<!-- pom.xml -->
<dependencies>
    <!-- Servlet API -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSP -->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
        <version>2.3.3</version>
        <scope>provided</scope>
    </dependency>

    <!-- MongoDB -->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.3.4</version>
    </dependency>

    <!-- Gson -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
```

---

## Phase 3: Integration & Deployment

### 3.1 Tasks

- [ ] **3.1.1** Local Web Service testing
- [ ] **3.1.2** Build ROOT.war
- [ ] **3.1.3** Deploy to GitHub Codespaces
- [ ] **3.1.4** Set port visibility to Public
- [ ] **3.1.5** Update Android app with Codespace URL
- [ ] **3.1.6** End-to-end testing
- [ ] **3.1.7** Error handling verification

### 3.2 Error Handling Checklist

- [ ] Invalid mobile app input
- [ ] Invalid server-side input
- [ ] Mobile app network failure
- [ ] Third-party API unavailable
- [ ] Third-party API invalid data

---

## Phase 4: Documentation & Submission

### 4.1 Tasks

- [ ] **4.1.1** Add Author comments to all source files
- [ ] **4.1.2** Create writeup PDF
- [ ] **4.1.3** Export IntelliJ project to ZIP
- [ ] **4.1.4** ZIP Android Studio project
- [ ] **4.1.5** Push all files to GitHub repository

### 4.2 Submission Files

- [ ] ROOT.war
- [ ] WebService.zip (IntelliJ export)
- [ ] App.zip (Android Studio)
- [ ] Writeup.pdf

---

## Data Flow

### Android Search Flow

```
1. User clicks Search
   ↓
2. MainActivity.onClick()
   → viewModel.search(query)
   ↓
3. MainViewModel.search()
   → searchUseCase.execute(query)
   → Update LiveData to Loading
   ↓
4. SearchUseCase.execute()
   → searchRepository.search(query)
   ↓
5. SearchRepositoryImpl.search()
   → remoteDataSource.fetchResults(query)
   ↓
6. RemoteDataSource.fetchResults()
   → HTTP GET to Web Service
   → Parse JSON → DTO
   → Convert DTO → Domain Model
   ↓
7. Result returns to ViewModel
   → Update LiveData to Success/Error
   ↓
8. MainActivity observes LiveData
   → Update UI with results
```

### Web Service Request Flow

```
1. SearchServlet.doGet()
   → Parse parameters
   → searchService.search(query, deviceInfo)
   ↓
2. SearchService.search()
   → tavilyClient.search(query)
   → Process response, extract data
   → logRepository.save(logEntry)
   → Return results
   ↓
3. SearchServlet
   → Convert to JSON
   → Return to Android
```

---

## API Endpoints

### Web Service

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/search` | GET | Handle search requests from Android |
| `/dashboard` | GET | Display operations dashboard |

### Request/Response Format

**Request:** `GET /search?query=xxx`

**Response:**
```json
{
  "status": "success",
  "results": [
    {
      "title": "Result Title",
      "url": "https://example.com",
      "snippet": "Result description..."
    }
  ]
}
```

---

## Timeline Estimate

| Phase | Duration |
|-------|----------|
| Phase 1: Android App | 3-4 hours |
| Phase 2: Web Service | 3-4 hours |
| Phase 3: Integration | 1-2 hours |
| Phase 4: Documentation | 1 hour |
| **Total** | **8-11 hours** |

---

## Notes

- Use Servlets, NOT JAX-RS
- MongoDB connection string: `mongodb+srv://qty20010619_db_user:****@cluster0.xrafh1j.mongodb.net/`
- Tavily API Key: `tvly-dev-****`
- Remember to make Codespace port Public for Android access
