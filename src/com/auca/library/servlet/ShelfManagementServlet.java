package com.auca.library.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auca.library.model.Room;
import com.auca.library.model.Shelf;
// Import your database utility class
import com.auca.library.util.DatabaseConnection;


@WebServlet("/ShelfManagementServlet")
public class ShelfManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Fetch and display existing shelves
        List<Shelf> shelves = fetchShelves();
        List<Room> rooms = fetchRooms();
        
        // Debug logging
        System.out.println("Total shelves found: " + shelves.size());
        System.out.println("Total rooms found: " + rooms.size());
        
        request.setAttribute("shelves", shelves);
        request.setAttribute("rooms", rooms);
        
        // Use forward with full path
        request.getRequestDispatcher("shelf_management.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "addShelf":
                addNewShelf(request);
                break;
            case "updateShelf":
                updateShelf(request);
                break;
            case "deleteShelf":
                deleteShelf(request);
                break;
        }
        
        // Redirect back to shelf management page
        doGet(request, response);
    }

    private void addNewShelf(HttpServletRequest request) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String shelfName = request.getParameter("shelfName");
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String category = request.getParameter("shelfCategory");
            String description = request.getParameter("description");

            String sql = "INSERT INTO shelves " +
                         "(shelf_name, room_id, capacity, shelf_category, description, " +
                         "current_book_count, status, created_at, updated_at) " +
                         "VALUES (?, ?, ?, ?, ?, 0, 'ACTIVE', NOW(), NOW())";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, shelfName);
                pstmt.setInt(2, roomId);
                pstmt.setInt(3, capacity);
                pstmt.setString(4, category);
                pstmt.setString(5, description);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Log error and handle appropriately
            e.printStackTrace();
        }
    }
    private void updateShelf(HttpServletRequest request) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            int shelfId = Integer.parseInt(request.getParameter("shelfId"));
            String shelfName = request.getParameter("shelfName");
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            int capacity = Integer.parseInt(request.getParameter("capacity"));

            String sql = "UPDATE shelves SET shelf_name = ?, room_id = ?, " +
                         "capacity = ?, updated_at = NOW() WHERE shelf_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, shelfName);
                pstmt.setInt(2, roomId);
                pstmt.setInt(3, capacity);
                pstmt.setInt(4, shelfId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteShelf(HttpServletRequest request) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            int shelfId = Integer.parseInt(request.getParameter("shelfId"));

            String sql = "DELETE FROM shelves WHERE shelf_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, shelfId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Shelf> fetchShelves() {
        List<Shelf> shelves = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT s.*, r.room_name FROM shelves s " +
                 "LEFT JOIN rooms r ON s.room_id = r.room_id")) {
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Shelf shelf = new Shelf();
                    shelf.setShelfId(rs.getInt("shelf_id"));
                    shelf.setShelfname(rs.getString("shelf_name"));
                    shelf.setRoomId(rs.getInt("room_id"));
                    shelf.setRoomName(rs.getString("room_name"));
                    shelf.setCapacity(rs.getInt("capacity"));
                    shelves.add(shelf);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelves;
    }

    private List<Room> fetchRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT room_id, room_name FROM rooms")) {
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Detailed logging
                System.out.println("Attempting to fetch rooms...");
                
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomName(rs.getString("room_name"));
                    rooms.add(room);
                    
                    // Logging each room found
                    System.out.println("Found Room: ID = " + room.getRoomId() + 
                                       ", Name = " + room.getRoomName());
                }
                
                // Log total rooms found
                System.out.println("Total rooms found: " + rooms.size());
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rooms:");
            e.printStackTrace();
        }
        return rooms;
    }
}