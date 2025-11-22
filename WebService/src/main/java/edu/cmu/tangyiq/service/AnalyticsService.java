package edu.cmu.tangyiq.service;

import edu.cmu.tangyiq.model.AnalyticsData;
import edu.cmu.tangyiq.repository.LogRepository;

public class AnalyticsService {
    private final LogRepository logRepository;

    public AnalyticsService() {
        this.logRepository = new LogRepository();
    }

    public AnalyticsData getAnalytics() {
        AnalyticsData data = new AnalyticsData();
        data.setTotalSearches(logRepository.getTotalSearches());
        data.setAverageLatency(logRepository.getAverageLatency());
        data.setTopQueries(logRepository.getTopQueries(10));
        data.setLogs(logRepository.getAllLogs());
        return data;
    }
}
