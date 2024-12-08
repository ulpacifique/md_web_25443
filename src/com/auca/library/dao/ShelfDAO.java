package com.auca.library.dao;



import com.auca.library.model.Shelf;
import com.auca.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ShelfDAO {
	private Connection connection;
	private static final Logger logger = Logger.getLogger(ShelfDAO.class.getName());
    // SQL Queries
	 private static final String INSERT_SHELF_SQL = 
		        "INSERT INTO shelves " +
		        "(shelf_name, room_id, capacity, shelf_category, status, description, created_at, updated_at) " +
		        "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";


	 private static final String SELECT_ALL_SHELVES_SQL = 
		        "SELECT s.shelf_id, s.shelf_name, s.room_id, r.room_name, " +
		        "s.capacity, s.current_book_count, s.shelf_category, " +
		        "s.status, s.description, s.created_at, s.updated_at " +
		        "FROM shelves s " +
		        "LEFT JOIN rooms r ON s.room_id = r.room_id";

    private static final String SELECT_SHELF_BY_ID_SQL = 
        "SELECT s.shelf_id, s.shelf_name, s.room_id, r.room_name, " +
        "s.capacity, s.current_book_count, s.shelf_category, " +
        "s.status, s.description, s.created_at, s.updated_at " +
        "FROM shelves s " +
        "LEFT JOIN rooms r ON s.room_id = r.room_id " +
        "WHERE s.shelf_id = ?";

    private static final String UPDATE_SHELF_SQL = 
        "UPDATE shelves SET " +
        "shelf_name = ?, room_id = ?, capacity = ?, " +
        "shelf_category = ?, status = ?, description = ?, updated_at = NOW() " +
        "WHERE shelf_id = ?";

    private static final String DELETE_SHELF_SQL = 
        "DELETE FROM shelves WHERE shelf_id = ?";

    /**
     * Create a new shelf
     * @param shelf Shelf object to be created
     * @return boolean indicating success of creation
     */
    public boolean createShelf(Shelf shelf) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SHELF_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, shelf.getShelfName());
            pstmt.setInt(2, shelf.getRoomId());
            pstmt.setInt(3, shelf.getCapacity());
            pstmt.setString(4, shelf.getShelfCategory());
            pstmt.setString(5, shelf.getStatus() != null ? shelf.getStatus() : Shelf.ShelfStatus.INACTIVE.name());
            pstmt.setString(6, shelf.getDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        shelf.setShelfId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieve all shelves
     * @return List of Shelf objects
     */
    public ShelfDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Shelf> getAllShelves() throws SQLException {
        List<Shelf> shelves = new ArrayList<>();
        String query = "SELECT * FROM shelves";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Shelf shelf = new Shelf();
                shelf.setShelfId(rs.getInt("shelf_id"));
                shelf.setShelfName(rs.getString("shelf_name"));
                shelf.setRoomId(rs.getInt("room_id"));
                shelf.setCapacity(rs.getInt("capacity"));
                
                // Set timestamps directly using Timestamp
                shelf.setCreatedAt(rs.getTimestamp("created_at"));
                shelf.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                shelf.setShelfCategory(rs.getString("shelf_category"));
                shelf.setCurrentBookCount(rs.getInt("current_book_count"));
                shelf.setStatus(rs.getString("status"));
                shelf.setDescription(rs.getString("description"));
                
                shelves.add(shelf);
            }
            
            return shelves;
        }
    }
    
    
    public void printAllShelvesRaw() throws SQLException {
        String sql = "SELECT * FROM shelves";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("Raw Shelf Data: " + 
                    "ID: " + rs.getInt("shelf_id") + 
                    ", Name: " + rs.getString("shelf_name") + 
                    ", Capacity: " + rs.getInt("capacity"));
            }
        }
    }


    /**
     * Retrieve a shelf by its ID
     * @param shelfId ID of the shelf
     * @return Shelf object or null if not found
     */
    public Shelf getShelfById(int shelfId) throws SQLException {
        String query = "SELECT * FROM shelves WHERE shelf_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, shelfId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Shelf shelf = new Shelf();
                    shelf.setShelfId(rs.getInt("shelf_id"));
                    shelf.setShelfName(rs.getString("shelf_name"));
                    shelf.setRoomId(rs.getInt("room_id"));
                    shelf.setCapacity(rs.getInt("capacity"));
                    
                    // Use Timestamp directly
                    shelf.setCreatedAt(rs.getTimestamp("created_at"));
                    shelf.setUpdatedAt(rs.getTimestamp("updated_at"));
                    
                    shelf.setShelfCategory(rs.getString("shelf_category"));
                    shelf.setCurrentBookCount(rs.getInt("current_book_count"));
                    shelf.setStatus(rs.getString("status"));
                    shelf.setDescription(rs.getString("description"));

                    logger.info("Shelf Found: " + shelf);
                    return shelf;
                } else {
                    logger.warning("No shelf found with ID: " + shelfId);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error in getShelfById", e);
            throw e;
        }
    }
    /**
     * Update an existing shelf
     * @param shelf Shelf object with updated information
     * @return boolean indicating success of update
     */
    public boolean updateShelf(Shelf shelf) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SHELF_SQL)) {
            
            pstmt.setString(1, shelf.getShelfName());
            pstmt.setInt(2, shelf.getRoomId());
            pstmt.setInt(3, shelf.getCapacity());
            pstmt.setString(4, shelf.getShelfCategory());
            pstmt.setString(5, shelf.getStatus());
            pstmt.setString(6, shelf.getDescription());
            pstmt.setInt(7, shelf.getShelfId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isShelfAvailable(int shelfId) throws SQLException {
        Shelf shelf = getShelfById(shelfId);
        
        if (shelf == null) {
            return false ;
        }

        // Check if the shelf has available capacity
        return shelf.getCurrentBookCount() < shelf.getCapacity();
    }

    public boolean updateShelfDetails(Shelf shelf) throws SQLException {
        String query = "UPDATE shelves SET " +
                       "shelf_name = ?, " +
                       "room_id = ?, " +
                       "capacity = ?, " +
                       "shelf_category = ?, " +
                       "current_book_count = ?, " +
                       "status = ?, " +
                       "description = ?, " +
                       "updated_at = CURRENT_TIMESTAMP " +
                       "WHERE shelf_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, shelf.getShelfName());
            stmt.setInt(2, shelf.getRoomId());
            stmt.setInt(3, shelf.getCapacity());
            stmt.setString(4, shelf.getShelfCategory());
            stmt.setInt(5, shelf.getCurrentBookCount());
            stmt.setString(6, shelf.getStatus());
            stmt.setString(7, shelf.getDescription());
            stmt.setInt(8, shelf.getShelfId());
            
            int rowsAffected = stmt.executeUpdate();
            
            logger.info("Updated shelf: " + shelf.getShelfId() + 
                        ", Rows Affected: " + rowsAffected);
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating shelf", e);
            throw e;
        }
    }

    /**
     * Delete a shelf by its ID
     * @param shelfId ID of the shelf to delete
     * @return boolean indicating success of deletion
     */
    public boolean deleteShelf(int shelfId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SHELF_SQL)) {
            
            pstmt.setInt(1, shelfId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map ResultSet to Shelf object
     * @param rs ResultSet from database query
     * @return Shelf object
     * @throws SQLException if database access error occurs
     */
    private Shelf mapResultSetToShelf(ResultSet rs) throws SQLException {
        Shelf shelf = new Shelf();
        shelf.setShelfId(rs.getInt("shelf_id"));
        shelf.setShelfName(rs.getString("shelf_name"));
        shelf.setRoomId(rs.getInt("room_id"));
        shelf.setRoomName(rs.getString("room_name"));
        shelf.setCapacity(rs.getInt("capacity"));
        shelf.setCurrentBookCount(rs.getInt("current_book_count"));
        shelf.setShelfCategory(rs.getString("shelf_category"));
        shelf.setStatus(rs.getString("status"));
        shelf.setDescription(rs.getString("description"));
        
        // Use Timestamp directly
        shelf.setCreatedAt(rs.getTimestamp("created_at"));
        shelf.setUpdatedAt(rs.getTimestamp("updated_at"));
        
        return shelf;
    }
    /**
     * Get shelves by room ID
     * @param roomId ID of the room
     * @return List of Shelves in the specified room
     */
    public List<Shelf> getShelfsByRoom(int roomId) {
        List<Shelf> shelves = new ArrayList<>();
        String sqlByRoom = SELECT_ALL_SHELVES_SQL + " WHERE s.room_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sqlByRoom)) {
            pstmt.setInt(1, roomId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Shelf shelf = mapResultSetToShelf(rs);
                    shelves.add(shelf);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return shelves;
    }

	public List<Shelf> getAllShelvesWithCapacity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 public int getBookCountOnShelf(int shelfId) throws SQLException {
	        String query = "SELECT COUNT(*) AS book_count FROM books WHERE shelf_id = ?";
	        
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, shelfId);
	            
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("book_count");
	                }
	                return 0;
	            }
	        } catch (SQLException e) {
	            logger.log(Level.SEVERE, "Error counting books on shelf", e);
	            throw e;
	        }
	    }
}