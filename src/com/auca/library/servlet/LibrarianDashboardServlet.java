package com.auca.library.servlet;

import com.auca.library.dao.MembershipDAO;
import com.auca.library.model.MembershipRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/librarianDashboard")
public class LibrarianDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembershipDAO membershipDAO = new MembershipDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<MembershipRequest> pendingRequests = membershipDAO.getPendingRequests();
            request.setAttribute("pendingRequests", pendingRequests);
            request.getRequestDispatcher("/librarian_dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving pending memberships", e);
        }
    }
}
