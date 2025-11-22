package edu.cmu.tangyiq.service;

import java.util.List;
import java.util.Map;

import edu.cmu.tangyiq.client.TavilyClient;
import edu.cmu.tangyiq.model.SearchLog;
import edu.cmu.tangyiq.repository.LogRepository;

public class SearchService {
    private final TavilyClient tavilyClient;
    private final LogRepository logRepository;

    public SearchService() {
        this.tavilyClient = new TavilyClient();
        this.logRepository = new LogRepository();
    }

    public static class SearchResult {
        public List<Map<String, String>> results;
        public String error;
    }

    public SearchResult search(String query, String deviceModel, String clientIP) {
        SearchResult result = new SearchResult();
        SearchLog log = new SearchLog();
        log.setQuery(query);
        log.setDeviceModel(deviceModel);
        log.setClientIP(clientIP);

        TavilyClient.SearchResponse response = tavilyClient.search(query);
        log.setTavilyLatency(response.latencyMs);

        if (response.error != null) {
            log.setStatus("error");
            log.setResultCount(0);
            result.error = response.error;
        } else {
            log.setStatus("success");
            log.setResultCount(response.results.size());
            result.results = response.results;
        }

        logRepository.save(log);
        return result;
    }
}
