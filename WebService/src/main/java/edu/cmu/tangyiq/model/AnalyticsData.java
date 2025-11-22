package edu.cmu.tangyiq.model;

import java.util.List;
import java.util.Map;

public class AnalyticsData {
    private List<Map.Entry<String, Long>> topQueries;
    private double averageLatency;
    private long totalSearches;
    private List<SearchLog> logs;

    public List<Map.Entry<String, Long>> getTopQueries() { return topQueries; }
    public void setTopQueries(List<Map.Entry<String, Long>> topQueries) { this.topQueries = topQueries; }

    public double getAverageLatency() { return averageLatency; }
    public void setAverageLatency(double averageLatency) { this.averageLatency = averageLatency; }

    public long getTotalSearches() { return totalSearches; }
    public void setTotalSearches(long totalSearches) { this.totalSearches = totalSearches; }

    public List<SearchLog> getLogs() { return logs; }
    public void setLogs(List<SearchLog> logs) { this.logs = logs; }
}
