/**
 * SearchLog - Model for search request log entry.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.model;

import java.util.Date;

public class SearchLog {
    private Date timestamp;
    private String query;
    private String deviceModel;
    private long tavilyLatency;
    private int resultCount;
    private String status;
    private String clientIP;

    public SearchLog() {
        this.timestamp = new Date();
    }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getDeviceModel() { return deviceModel; }
    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

    public long getTavilyLatency() { return tavilyLatency; }
    public void setTavilyLatency(long tavilyLatency) { this.tavilyLatency = tavilyLatency; }

    public int getResultCount() { return resultCount; }
    public void setResultCount(int resultCount) { this.resultCount = resultCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getClientIP() { return clientIP; }
    public void setClientIP(String clientIP) { this.clientIP = clientIP; }
}
