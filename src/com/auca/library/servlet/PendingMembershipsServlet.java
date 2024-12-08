package com.auca.library.servlet;

import com.auca.library.dao.MembershipDAO;
import com.auca.library.model.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/pendingMemberships")
public class PendingMembershipsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembershipDAO membershipDAO = new MembershipDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Member> pendingMembers = membershipDAO.getPendingMembers();
        request.setAttribute("pendingMembers", pendingMembers);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("pendingMemberships.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String memberIdStr = request.getParameter("memberId");

            if (memberIdStr == null || memberIdStr.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Member ID is missing");
                doGet(request, response);
                return;
            }

            try {
                int memberId = Integer.parseInt(memberIdStr);
                String status;
                
                if ("Approve".equals(action)) {
                    status = "APPROVED";
                } else if ("Reject".equals(action)) {
                    status = "REJECTED";
                } else {
                    request.setAttribute("errorMessage", "Invalid action specified");
                    doGet(request, response);
                    return;
                }

                membershipDAO.updateMembershipStatus(memberId, status);
                response.sendRedirect("pendingMemberships");
                
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid member ID format");
                doGet(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            doGet(request, response);
        }
    }
}