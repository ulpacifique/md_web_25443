package com.auca.library.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.dao.RoomDAO;
import com.auca.library.model.Room;
import com.auca.library.model.Shelf;
import com.auca.library.util.DatabaseConnection;

@WebServlet("/ShelfAssignmentServlet")
public class ShelfAssignmentServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoomDAO roomDAO;

    public void init() throws ServletException {
        roomDAO = new RoomDAO();
        try {
			roomDAO.setConnection(DatabaseConnection.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Shelf> unassignedShelves = roomDAO.getUnassignedShelves();
            List<Room> rooms = roomDAO.getAllRooms();

            request.setAttribute("unassignedShelves", unassignedShelves);
            request.setAttribute("rooms", rooms);

            request.getRequestDispatcher("/assign-shelf.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching shelf data", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int shelfId = Integer.parseInt(request.getParameter("shelfId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));

        try {
            boolean success = roomDAO.assignShelfToRoom(shelfId, roomId);
            if (success) {
                request.setAttribute("message", "Shelf successfully assigned to room!");
            } else {
                request.setAttribute("error", "Failed to assign shelf to room.");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        doGet(request, response);
    }
}