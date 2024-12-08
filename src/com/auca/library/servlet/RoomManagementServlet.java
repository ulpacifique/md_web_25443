package com.auca.library.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auca.library.dao.RoomDAO;
import com.auca.library.model.Room;
import com.auca.library.util.DatabaseConnection;

@WebServlet("/RoomManagementServlet")
public class RoomManagementServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            roomDAO = new RoomDAO();
            roomDAO.setConnection(connection);
        } catch (Exception e) {
            throw new ServletException("Error initializing database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Fetch rooms with error handling
            List<Room> rooms = roomDAO.getAllRooms();
            
            // Log rooms for debugging
            logRooms(rooms);
            
            // Handle empty room list
            if (rooms.isEmpty()) {
                request.setAttribute("infoMessage", "No rooms found in the database.");
            }
            
            // Set rooms attribute
            request.setAttribute("rooms", rooms);
            
            // Forward to JSP
            request.getRequestDispatcher("room_management.jsp").forward(request, response);
        
        } catch (Exception e) {
            // Comprehensive error handling
            log("Error in doGet", e);
            
            // Set error attribute
            request.setAttribute("errorMessage", 
                "Unable to retrieve rooms: " + e.getMessage());
            
            // Forward to error page
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Set character encoding to handle potential UTF-8 issues
            request.setCharacterEncoding("UTF-8");
            
            // Create a new room
            createRoom(request, response);
        } catch (SQLException e) {
            // Log the full error
            e.printStackTrace();
            
            // Set error attribute and forward to error page
            request.setAttribute("errorMessage", "Error creating room: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Room> rooms = roomDAO.getAllRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/WEB-INF/views/room-list.jsp").forward(request, response);
    }

    private void showNewRoomForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/room-form.jsp").forward(request, response);
    }

    private void createRoom(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException, NumberFormatException {
        // Debugging: Print out all parameters
        request.getParameterMap().forEach((key, values) -> {
            System.out.println(key + ": " + String.join(", ", values));
        });

        // Create a new Room object
        Room room = new Room();
        
        // Safely parse and set room properties with null checks
        try {
            // Room Name
            String roomName = request.getParameter("roomName");
            if (roomName == null || roomName.trim().isEmpty()) {
                throw new IllegalArgumentException("Room Name is required");
            }
            room.setRoomName(roomName);

            // Description
            String description = request.getParameter("roomDescription");
            room.setDescription(description != null ? description : "");

            // Capacity
            String capacityStr = request.getParameter("capacity");
            if (capacityStr == null || capacityStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Capacity is required");
            }
            room.setCapacity(Integer.parseInt(capacityStr));

            // Location (Building ID) - Modified to use buildingId
            String buildingIdStr = request.getParameter("buildingId");
            if (buildingIdStr == null || buildingIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Building ID is required");
            }
            room.setBuildingId(Integer.parseInt(buildingIdStr));

            // Set default values for other required fields
            room.setRoomType(Room.RoomType.LIBRARY); // Default room type
            
            // Generate a room number if not provided
            room.setRoomNumber(generateRoomNumber(room.getBuildingId()));
            
            room.setFloorNumber(1); // Default floor
            room.setActive(true); // Default to active

            // Insert the room
            roomDAO.insertRoom(room);

            // Redirect to room list or show success message
            response.sendRedirect(request.getContextPath() + "/RoomManagementServlet");
        
        } catch (IllegalArgumentException e) {
            // Handle parsing errors
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/room-form.jsp").forward(request, response);
        }
    }

    // Helper method to generate a room number
    private String generateRoomNumber(int buildingId) {
        // You can implement a more sophisticated room number generation logic
        return "B" + buildingId + "-R" + System.currentTimeMillis() % 1000;
    }
    
    private void logRooms(List<Room> rooms) {
        System.out.println("Rooms Fetched: " + rooms.size());
        for (Room room : rooms) {
            System.out.printf("Room ID: %d, Name: %s, Capacity: %d%n", 
                room.getRoomId(), room.getRoomName(), room.getCapacity());
        }
    }

    private void viewRoom(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("id"));
        Room room = roomDAO.getRoomById(roomId);
        
        if (room != null) {
            request.setAttribute("room", room);
            request.getRequestDispatcher("/WEB-INF/views/room-detail.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
        }
    }

    // Update an existing room
    private void updateRoom(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        // Similar to createRoom, but with an existing room ID
        // You'll need to add an updateRoom method in RoomDAO
    }

    // Delete a room
    private void deleteRoom(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("id"));
        // You'll need to add a deleteRoom method in RoomDAO

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\": \"Room deleted successfully\"}");
    }

    // Cleanup resources
    @Override
    public void destroy() {
        DatabaseConnection.closeConnection(null);
    }
}