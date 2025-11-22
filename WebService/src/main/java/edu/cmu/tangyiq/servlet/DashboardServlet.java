package edu.cmu.tangyiq.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import edu.cmu.tangyiq.model.AnalyticsData;
import edu.cmu.tangyiq.service.AnalyticsService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private AnalyticsService analyticsService;

    @Override
    public void init() throws ServletException {
        analyticsService = new AnalyticsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AnalyticsData analytics = analyticsService.getAnalytics();
        request.setAttribute("analytics", analytics);
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
