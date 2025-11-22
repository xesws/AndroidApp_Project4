package edu.cmu.tangyiq.testing;

import edu.cmu.tangyiq.client.TavilyClient;
import edu.cmu.tangyiq.repository.LogRepository;

public class WebServiceTest {

    public static void main(String[] args) {
        System.out.println("=== Web Service Integration Test ===\n");

        testTavilyClient();
        testMongoDBConnection();

        System.out.println("\n=== All tests completed ===");
    }

    private static void testTavilyClient() {
        System.out.println("Testing Tavily API...");
        try {
            TavilyClient client = new TavilyClient();
            TavilyClient.SearchResponse response = client.search("Pittsburgh weather");

            if (response.error != null) {
                System.out.println("✗ Tavily API error: " + response.error);
            } else {
                System.out.println("✓ Tavily API success");
                System.out.println("  - Results: " + response.results.size());
                System.out.println("  - Latency: " + response.latencyMs + "ms");
                if (!response.results.isEmpty()) {
                    System.out.println("  - First result: " + response.results.get(0).get("title"));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Tavily API exception: " + e.getMessage());
        }
    }

    private static void testMongoDBConnection() {
        System.out.println("\nTesting MongoDB connection...");
        try {
            LogRepository repo = new LogRepository();
            long count = repo.getTotalSearches();
            System.out.println("✓ MongoDB connection success");
            System.out.println("  - Total searches in DB: " + count);
            repo.close();
        } catch (Exception e) {
            System.out.println("✗ MongoDB connection error: " + e.getMessage());
        }
    }
}
