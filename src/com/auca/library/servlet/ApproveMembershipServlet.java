package com.auca.library.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import com.auca.library.dao.MembershipDAO;

@WebServlet("/approveMembership")
public class ApproveMembershipServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembershipDAO membershipDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        membershipDAO = new MembershipDAO(); // Initialize MembershipDAO
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get parameters
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String membershipType = request.getParameter("membershipType");

            // Approve the membership request
            membershipDAO.updateMembershipStatus(requestId, "APPROVED", membershipType);

            // Redirect back to the pending memberships page
            response.sendRedirect(request.getContextPath() + "/pendingMemberships");
        } catch (NumberFormatException e) {
            logError("Invalid request ID format", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request ID format");
        } catch (HibernateException e) {
            logError("Database error while processing membership request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (Exception e) {
            logError("Error processing membership request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private void logError(String message, Exception e) {
        // Implement your logging mechanism here, e.g., using a logging framework
        System.err.println(message);
        e.printStackTrace();
    }
}
