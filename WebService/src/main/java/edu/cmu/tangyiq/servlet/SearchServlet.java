package edu.cmu.tangyiq.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import edu.cmu.tangyiq.service.SearchService;
import edu.cmu.tangyiq.util.JsonUtil;

/**
 * SearchServlet - HTTP endpoint for search requests from Android app.
 * Handles GET /search?query=xxx
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private SearchService searchService;

    @Override
    public void init() throws ServletException {
        searchService = new SearchService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("error", "Query parameter is required");
            out.print(JsonUtil.toJson(errorResponse));
            return;
        }

        String userAgent = request.getHeader("User-Agent");
        String deviceModel = parseDeviceModel(userAgent);
        String clientIP = request.getRemoteAddr();

        SearchService.SearchResult result = searchService.search(query.trim(), deviceModel, clientIP);

        Map<String, Object> jsonResponse = new HashMap<>();
        if (result.error != null) {
            jsonResponse.put("status", "error");
            jsonResponse.put("error", result.error);
        } else {
            jsonResponse.put("status", "success");
            jsonResponse.put("results", result.results);
        }

        out.print(JsonUtil.toJson(jsonResponse));
    }

    private String parseDeviceModel(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Android")) {
            int start = userAgent.indexOf("Android");
            int end = userAgent.indexOf(")", start);
            if (end > start) {
                return userAgent.substring(start, end);
            }
        }
        return userAgent.length() > 50 ? userAgent.substring(0, 50) : userAgent;
    }
}
