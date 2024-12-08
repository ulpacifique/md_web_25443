package com.auca.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auca.library.dao.BookDAO;
import com.auca.library.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Get parameters from the request
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int borrowedBookId = Integer.parseInt(request.getParameter("borrowedBookId"));

            // Get the logged-in user's ID from session
            HttpSession session = request.getSession();
            Integer memberId = (Integer) session.getAttribute("userId");

            if (memberId == null) {
                sendErrorResponse(out, "User not logged in");
                return;
            }

            // Use BookDAO to return the book
            BookDAO bookDAO = new BookDAO(DatabaseConnection.getConnection());
            double fineAmount = bookDAO.returnBook(borrowedBookId, memberId);

            if (fineAmount >= 0) {
                // Successful return
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("fineAmount", fineAmount);
                out.println(gson.toJson(jsonResponse));
            } else {
                sendErrorResponse(out, "Failed to return book");
            }

        } catch (NumberFormatException e) {
            sendErrorResponse(out, "Invalid book ID");
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(out, "An error occurred while returning the book");
        }
    }

    private void sendErrorResponse(PrintWriter out, String message) throws IOException {
        JsonObject errorResponse = new JsonObject();
        errorResponse.addProperty("success", false);
        errorResponse.addProperty("message", message);
        out.println(gson.toJson(errorResponse));
    }
}