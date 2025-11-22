# Project 4 - Task 1: Demonstration of Project Feasibility

**Author:** Tangyi Qian
**Andrew ID:** tangyiq

---

## API Information

### 1. API Name
**Tavily Search API**

### 2. API Documentation URL
https://docs.tavily.com/

### 3. Mobile Application Description
My Android application will prompt the user to input a search query, then call the Tavily Search API to fetch relevant search results and display them to users. The application adopts a modern Clean Architecture pattern, including UI layer, business logic layer, and data layer, providing a smooth search experience. The application will also log each search for later operational analysis.

---

## Demo Screenshots

### 4. Tavily Search API Demo - Console Output

```
=== Tavily API Demo ===
Searching for: Carnegie Mellon University

Search Results:
---------------
1. Carnegie Mellon University | CMU
   URL: https://www.cmu.edu/
   Snippet: A private, global research university, Carnegie Mellon stands among the world's top educational institutions with its cutting-edge programs and innova...

2. Carnegie Mellon University
   URL: https://www.facebook.com/carnegiemellonu/
   Snippet: A private, global research university. One of the world's most renowned educational institutions.

3. Carnegie Mellon University (@carnegiemellon)
   URL: https://www.instagram.com/carnegiemellon/?hl=en
   Snippet: 111K followers · 2.5K+ posts · Our color is a tartan, we race buggies & have a poker-playing robot. We've got our own way of doing things. #LifeAtCMU.

4. Carnegie Mellon University - Profile, Rankings and Data
   URL: https://www.usnews.com/best-colleges/carnegie-mellon-university-3242
   Snippet: My Schools The student-faculty ratio at Carnegie Mellon University is 6:1, and it utilizes a semester-based academic calendar. The student-faculty rat...

5. Carnegie Mellon University
   URL: https://en.wikipedia.org/wiki/Carnegie_Mellon_University
   Snippet: Jump to content ## Contents * 1 History * Bahasa Indonesia * Bahasa Melayu * Norsk bokmål * Norsk nynorsk * Download QR code ## Hist...

=== Demo Complete ===
```

### 5. MongoDB Atlas Demo - Console Output

```
=== MongoDB Atlas Demo ===

Connected to MongoDB Atlas successfully!

Enter a string to store in the database: Hello MongoDB Task 1!
String written to database successfully!

All strings stored in the database:
-----------------------------------
1. Hello from Task 1 Demo
2. Hello MongoDB Task 1!

Total documents: 2

=== Demo Complete ===
```

---

## Summary

This document demonstrates the feasibility of using the Tavily Search API and MongoDB Atlas for Project 4 Task 2. Both demos successfully:

1. **Tavily Search API Demo**: Successfully fetched and displayed search results from Carnegie Mellon University query using the Tavily API with structured JSON data extraction.

2. **MongoDB Atlas Demo**: Successfully connected to MongoDB Atlas cloud database, wrote user input to the database, and read back all stored documents.

These demonstrations confirm that the selected technologies are viable for the planned distributed application.
