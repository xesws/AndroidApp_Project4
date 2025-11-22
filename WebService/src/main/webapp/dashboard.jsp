<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.cmu.tangyiq.model.AnalyticsData" %>
<%@ page import="edu.cmu.tangyiq.model.SearchLog" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tavily Search Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        h1 {
            color: #333;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
        }
        .card {
            background: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .card h2 {
            margin-top: 0;
            color: #007bff;
        }
        .metric {
            font-size: 2em;
            font-weight: bold;
            color: #333;
        }
        .metric-label {
            color: #666;
            font-size: 0.9em;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        .author {
            text-align: center;
            color: #666;
            margin-top: 40px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Tavily Search - Operations Dashboard</h1>

        <%
            AnalyticsData analytics = (AnalyticsData) request.getAttribute("analytics");
        %>

        <div class="grid">
            <div class="card">
                <h2>Total Searches</h2>
                <div class="metric"><%= analytics != null ? analytics.getTotalSearches() : 0 %></div>
                <div class="metric-label">All time search requests</div>
            </div>

            <div class="card">
                <h2>Average Latency</h2>
                <div class="metric"><%= analytics != null ? String.format("%.0f", analytics.getAverageLatency()) : 0 %> ms</div>
                <div class="metric-label">Tavily API response time</div>
            </div>
        </div>

        <div class="card">
            <h2>Top 10 Search Queries</h2>
            <table>
                <tr>
                    <th>Rank</th>
                    <th>Query</th>
                    <th>Count</th>
                </tr>
                <% if (analytics != null && analytics.getTopQueries() != null) {
                    int rank = 1;
                    for (Map.Entry<String, Long> entry : analytics.getTopQueries()) { %>
                <tr>
                    <td><%= rank++ %></td>
                    <td><%= entry.getKey() %></td>
                    <td><%= entry.getValue() %></td>
                </tr>
                <%  }
                } %>
            </table>
        </div>

        <div class="card">
            <h2>Full Request Logs</h2>
            <table>
                <tr>
                    <th>Timestamp</th>
                    <th>Query</th>
                    <th>Device</th>
                    <th>Latency (ms)</th>
                    <th>Results</th>
                    <th>Status</th>
                    <th>Client IP</th>
                </tr>
                <% if (analytics != null && analytics.getLogs() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (SearchLog log : analytics.getLogs()) { %>
                <tr>
                    <td><%= log.getTimestamp() != null ? sdf.format(log.getTimestamp()) : "N/A" %></td>
                    <td><%= log.getQuery() %></td>
                    <td><%= log.getDeviceModel() %></td>
                    <td><%= log.getTavilyLatency() %></td>
                    <td><%= log.getResultCount() %></td>
                    <td><%= log.getStatus() %></td>
                    <td><%= log.getClientIP() %></td>
                </tr>
                <%  }
                } %>
            </table>
        </div>

        <div class="author">
            <p>Author: Tangyi Qian | Andrew ID: tangyiq</p>
        </div>
    </div>
</body>
</html>
