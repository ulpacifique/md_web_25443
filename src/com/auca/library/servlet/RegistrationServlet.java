package com.auca.library.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.dao.MemberDao;
import com.auca.library.model.Member;
import com.auca.library.model.MembershipRequest;
import com.auca.library.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberDao memberDao = new MemberDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            transaction = session.beginTransaction();
            
            // Retrieve and validate form parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String membershipType = request.getParameter("membershipType");
            String role = request.getParameter("role");

            if (!isFieldValid(firstName) || !isFieldValid(lastName) || !isFieldValid(email) 
                    || !isFieldValid(password) || !isFieldValid(membershipType) || !isFieldValid(role)) {
                request.setAttribute("errorMessage", "All fields are required.");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
                return;
            }
            
            // Set up Member
            Member member = new Member();
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setEmail(email);
            member.setPassword(hashPassword(password));
            member.setMembershipType(membershipType);
            member.setRole(role);
            member.setApproved(false);
            member.setApprovalStatus("PENDING");
            member.setBorrowedBooks(0);

            // Save the Member in the database first
            session.save(member);
            
            // Create and save the MembershipRequest
            MembershipRequest membershipRequest = new MembershipRequest();
            membershipRequest.setMember(member); // Link saved member here
            membershipRequest.setFirstName(firstName);
            membershipRequest.setLastName(lastName);
            membershipRequest.setEmail(email);
            membershipRequest.setMembershipType(membershipType);
            membershipRequest.setRequestDate(java.time.LocalDate.now().toString());
            membershipRequest.setStatus("PENDING");
            membershipRequest.setApprovalStatus("PENDING");

            session.save(membershipRequest); // Save MembershipRequest

            transaction.commit();
            response.sendRedirect("signin1.html");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        } finally {
            session.close(); // Ensure session is closed here
        }
    }

    private String hashPassword(String password) {
        try {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isFieldValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
