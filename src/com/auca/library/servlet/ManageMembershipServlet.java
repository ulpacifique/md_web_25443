package com.auca.library.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.auca.library.dao.MembershipDAO;

@WebServlet("/manageMembership")
public class ManageMembershipServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembershipDAO membershipDAO = new MembershipDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String action = request.getParameter("action");

            if ("Approve".equals(action)) {
                membershipDAO.updateMembershipStatus(requestId, "APPROVED");
            } else if ("Reject".equals(action)) {
                membershipDAO.updateMembershipStatus(requestId, "REJECTED");
            } else {
                throw new IllegalArgumentException("Invalid action: " + action);
            }

            response.sendRedirect(request.getContextPath() + "/pendingMemberships");
        } catch (NumberFormatException e) {
            // Log the error
            e.printStackTrace();
            throw new ServletException("Invalid request ID format", e);
        } catch (HibernateException e) {
            // Log the error
            e.printStackTrace();
            throw new ServletException("Database error while managing membership", e);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            throw new ServletException("Error managing membership", e);
        }
    }
}