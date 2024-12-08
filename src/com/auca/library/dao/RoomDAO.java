package com.auca.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.auca.library.model.Book;
import com.auca.library.model.Room;
import com.auca.library.model.Shelf;
import com.auca.library.util.DatabaseConnection;

public class RoomDAO {
	private Connection connection;
	
	 public void setConnection(Connection connection) {
	        if (connection == null) {
	            throw new IllegalArgumentException("Database connection cannot be null");
	        }
	        this.connection = connection;
	    }
    // Method to count books in a specific room
    public int countBooksInRoom(int roomId) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT b.book_id) AS book_count " +
                     "FROM books b " +
                     "JOIN book_shelf_assignments bsa ON b.book_id = bsa.book_id " +
                     "JOIN shelves s ON bsa.shelf_id = s.shelf_id " +
                     "WHERE s.room_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, roomId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("book_count");
                }
            }
        }
        
        return 0;
    }

    // Method to get books in a specific room
    public List<Book> getBooksInRoom(int roomId) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT DISTINCT b.book_id, b.title, b.author, b.isbn " +
                     "FROM books b " +
                     "JOIN book_shelf_assignments bsa ON b.book_id = bsa.book_id " +
                     "JOIN shelves s ON bsa.shelf_id = s.shelf_id " +
                     "WHERE s.room_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, roomId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setIsbn(rs.getString("isbn"));
                    books.add(book);
                }
            }
        }
        
        return books;
    }

    // Method to get room details with book count
    public Room getRoomWithBookCount(int roomId) throws SQLException {
        Room room = getRoomById(roomId);
        if (room != null) {
            room.setBookCount(countBooksInRoom(roomId));
        }
        return room;
    }

    public Room getRoomById(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	// Method to get all rooms with their book counts
 

    

	// Additional method to get rooms by type
    public List<Room> getRoomsByType(Room.RoomType roomType) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE room_type = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, roomType.name());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomName(rs.getString("room_name"));
                    room.setBuildingId(rs.getInt("building_id"));
                    room.setRoomType(Room.RoomType.valueOf(rs.getString("room_type")));
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setCapacity(rs.getInt("capacity"));
                    room.setFloorNumber(rs.getInt("floor_number"));
                    room.setActive(rs.getBoolean("is_active"));
                    room.setDescription(rs.getString("description"));
                    
                    // Set book count
                    room.setBookCount(countBooksInRoom(room.getRoomId()));
                    
                    rooms.add(room);
                }
            }
        }
        
        return rooms;
    }

    // Method to check room occupancy
    public double getRoomOccupancy(int roomId) throws SQLException {
        String sql = "SELECT " +
                     "  COUNT(DISTINCT b.book_id) AS book_count, " +
                     "  r.capacity " +
                     "FROM rooms r " +
                     "LEFT JOIN shelves s ON r.room_id = s.room_id " +
                     "LEFT JOIN book_shelf_assignments bsa ON s.shelf_id = bsa.shelf_id " +
                     "LEFT JOIN books b ON bsa.book_id = b.book_id " +
                     "WHERE r.room_id = ? " +
                     "GROUP BY r.room_id, r.capacity";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, roomId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int bookCount = rs.getInt("book_count");
                    int capacity = rs.getInt("capacity");
                    
                    return capacity > 0 ? (double) bookCount / capacity * 100 : 0;
                }
            }
        }
        
        return 0;
    }
    public List<Room> getAllRoomsWithBookCounts() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, COUNT(b.book_id) AS book_count " +
                     "FROM rooms r LEFT JOIN shelves s ON r.room_id = s.room_id " +
                     "LEFT JOIN book_shelf_assignments bsa ON s.shelf_id = bsa.shelf_id " +
                     "LEFT JOIN books b ON bsa.book_id = b.book_id " +
                     "GROUP BY r.room_id, r.room_name, r.building_id, r.room_type, " +
                     "r.room_number, r.capacity, r.floor_number, r.is_active, r.description";
        
        // Ensure we have a valid connection
        Connection conn = null;
        try {
            // If no connection is set, get a new one
            if (connection == null || connection.isClosed()) {
                conn = DatabaseConnection.getConnection();
            } else {
                conn = connection;
            }
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomName(rs.getString("room_name"));
                    room.setBuildingId(rs.getInt("building_id"));
                    
                    // Safely handle room type
                    String roomTypeStr = rs.getString("room_type");
                    if (roomTypeStr != null) {
                        room.setRoomType(Room.RoomType.valueOf(roomTypeStr));
                    }
                    
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setCapacity(rs.getInt("capacity"));
                    room.setFloorNumber(rs.getInt("floor_number"));
                    room.setActive(rs.getBoolean("is_active"));
                    room.setDescription(rs.getString("description"));
                    room.setBookCount(rs.getInt("book_count"));
                    
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            // Log the error
            System.err.println("Error retrieving rooms: " + e.getMessage());
            throw e;
        } finally {
            // Close the connection if we opened a new one
            if (conn != null && connection == null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
        
        return rooms;
    }
    
    
	
	public void deleteRoom(int roomId) {
		// TODO Auto-generated method stub
		
	}

	public void updateRoom(Room updatedRoom) {
		// TODO Auto-generated method stub
		
	}

	public void createRoom(Room newRoom) {
		// TODO Auto-generated method stub
		
	}

	 public void insertRoom(Room room) throws SQLException {
	        if (connection == null) {
	            throw new SQLException("Database connection is null");
	        }

	        String sql = "INSERT INTO rooms (room_name, description, capacity, building_id) " +
	                     "VALUES (?, ?, ?, ?)";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setString(1, room.getRoomName());
	            pstmt.setString(2, room.getDescription());
	            pstmt.setInt(3, room.getCapacity());
	            pstmt.setInt(4, room.getBuildingId());
	            
	            pstmt.executeUpdate();
	        }
	    }
	 public List<Room> getAllRooms() {
	        List<Room> rooms = new ArrayList<>();
	        
	        if (connection == null) {
	            System.err.println("Database connection is null. Cannot fetch rooms.");
	            return rooms;
	        }

	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            String sql = "SELECT * FROM rooms";
	            pstmt = connection.prepareStatement(sql);
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                Room room = new Room();
	                room.setRoomId(rs.getInt("room_id"));
	                room.setRoomName(rs.getString("room_name"));
	                room.setDescription(rs.getString("description"));
	                room.setCapacity(rs.getInt("capacity"));
	                room.setBuildingId(rs.getInt("building_id"));
	                
	                rooms.add(room);
	            }
	        } catch (SQLException e) {
	            System.err.println("Error fetching rooms: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            // Properly close resources
	            try {
	                if (rs != null) rs.close();
	                if (pstmt != null) pstmt.close();
	            } catch (SQLException e) {
	                System.err.println("Error closing database resources: " + e.getMessage());
	            }
	        }

	        return rooms;
	    }
	 
	// Method to assign a shelf to a room
	 public boolean assignShelfToRoom(int shelfId, int roomId) throws SQLException {
	     if (connection == null) {
	         connection = DatabaseConnection.getConnection();
	     }

	     String sql = "UPDATE shelves SET room_id = ? WHERE shelf_id = ?";
	     
	     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	         pstmt.setInt(1, roomId);
	         pstmt.setInt(2, shelfId);
	         
	         int affectedRows = pstmt.executeUpdate();
	         return affectedRows > 0;
	     }
	 }

	 // Method to get unassigned shelves
	 public List<Shelf> getUnassignedShelves() throws SQLException {
		    List<Shelf> unassignedShelves = new ArrayList<>();
		    
		    if (connection == null) {
		        connection = DatabaseConnection.getConnection();
		    }

		    String sql = "SELECT shelf_id, shelf_name, capacity " +
		                 "FROM shelves " +
		                 "WHERE room_id IS NULL OR room_id = 0";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {
		        
		        while (rs.next()) {
		            Shelf shelf = new Shelf();
		            shelf.setShelfId(rs.getInt("shelf_id"));
		            shelf.setShelfName(rs.getString("shelf_name"));
		            shelf.setShelfCapacity(rs.getInt("capacity"));
		            unassignedShelves.add(shelf);
		        }
		    }
		    
		    return unassignedShelves;
		}
	 // Method to get shelves in a specific room
	 public List<Shelf> getShelvesInRoom(int roomId) throws SQLException {
	     List<Shelf> roomShelves = new ArrayList<>();
	     
	     if (connection == null) {
	         connection = DatabaseConnection.getConnection();
	     }

	     String sql = "SELECT shelf_id, shelf_name, shelf_capacity, " +
	                  "(SELECT COUNT(book_id) FROM book_shelf_assignments WHERE shelf_id = s.shelf_id) AS current_books " +
	                  "FROM shelves s " +
	                  "WHERE room_id = ?";
	     
	     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	         pstmt.setInt(1, roomId);
	         
	         try (ResultSet rs = pstmt.executeQuery()) {
	             while (rs.next()) {
	                 Shelf shelf = new Shelf();
	                 shelf.setShelfId(rs.getInt("shelf_id"));
	                 shelf.setShelfName(rs.getString("shelf_name"));
	                 shelf.setShelfCapacity(rs.getInt("shelf_capacity"));
	                 shelf.setCurrentBookCount(rs.getInt("current_books"));
	                 roomShelves.add(shelf);
	             }
	         }
	     }
	     
	     return roomShelves;
	 }

	 // Method to check room's shelf capacity and current book count
	 public RoomShelfStatus getRoomShelfStatus(int roomId) throws SQLException {
	     RoomShelfStatus status = new RoomShelfStatus();
	     
	     if (connection == null) {
	         connection = DatabaseConnection.getConnection();
	     }

	     String sql = "SELECT " +
	                  "  COUNT(DISTINCT s.shelf_id) AS total_shelves, " +
	                  "  SUM(s.shelf_capacity) AS total_shelf_capacity, " +
	                  "  COUNT(DISTINCT b.book_id) AS total_books " +
	                  "FROM rooms r " +
	                  "LEFT JOIN shelves s ON r.room_id = s.room_id " +
	                  "LEFT JOIN book_shelf_assignments bsa ON s.shelf_id = bsa.shelf_id " +
	                  "LEFT JOIN books b ON bsa.book_id = b.book_id " +
	                  "WHERE r.room_id = ?";
	     
	     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	         pstmt.setInt(1, roomId);
	         
	         try (ResultSet rs = pstmt.executeQuery()) {
	             if (rs.next()) {
	                 status.setRoomId(roomId);
	                 status.setTotalShelves(rs.getInt("total_shelves"));
	                 status.setTotalShelfCapacity(rs.getInt("total_shelf_capacity"));
	                 status.setCurrentBookCount(rs.getInt("total_books"));
	                 
	                 // Calculate occupancy percentage
	                 int totalCapacity = status.getTotalShelfCapacity();
	                 int currentBooks = status.getCurrentBookCount();
	                 status.setOccupancyPercentage(totalCapacity > 0 ? 
	                     (double) currentBooks / totalCapacity * 100 : 0);
	             }
	         }
	     }
	     
	     return status;
	 }
	
	
}