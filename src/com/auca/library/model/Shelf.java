package com.auca.library.model;

import java.sql.Timestamp;

public class Shelf {
    private int shelfId;
    private String shelfName;
    private int roomId;
    private String roomName; // To store associated room name
    private int capacity;
    private int currentBookCount;
    private String shelfCategory;
    private String status;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
  
    private int shelfCapacity;

    // Enum for Shelf Category
    public enum ShelfCategory {
        FICTION("Fiction"),
        NON_FICTION("Non-Fiction"),
        REFERENCE("Reference"),
        PERIODICALS("Periodicals"),
        GENERAL("General");

        private final String displayName;

        ShelfCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Enum for Shelf Status
    public enum ShelfStatus {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        FULL("Full");

        private final String displayName;

        ShelfStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public Shelf() {
        this.shelfId = shelfId;
        this.shelfName = shelfName;
        this.capacity = capacity;
        this.currentBookCount = 0;
    }

    
	// Comprehensive Getters
    public int getShelfId() {
        return shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentBookCount() {
        return currentBookCount;
    }

    public String getShelfCategory() {
        return shelfCategory;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Comprehensive Setters
    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        updateShelfStatus(); // Update status when capacity is set
    }

    public void setCurrentBookCount(int currentBookCount) {
        this.currentBookCount = currentBookCount;
        updateShelfStatus(); // Update status when book count changes
    }

    public void setShelfCategory(String shelfCategory) {
        this.shelfCategory = shelfCategory;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Additional Method to Check Shelf Capacity
    public boolean hasRemainingCapacity() {
        return currentBookCount < capacity;
    }

    // Method to update book count
    public void addBook() {
        if (hasRemainingCapacity()) {
            this.currentBookCount++;
            updateShelfStatus();
        } else {
            throw new IllegalStateException("Shelf is at full capacity");
        }
    }

    public void removeBook() {
        if (currentBookCount > 0) {
            this.currentBookCount--;
            updateShelfStatus();
        }
    }

    private void updateShelfStatus() {
        if (currentBookCount >= capacity) {
            this.status = ShelfStatus.FULL.name();
        } else if (currentBookCount > 0) {
            this.status = ShelfStatus.ACTIVE.name();
        } else {
            this.status = ShelfStatus.INACTIVE.name();
        }
    }

    // toString method for debugging
   

	public void setShelfname(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setShelfCapacity(int int1) {
		// TODO Auto-generated method stub
		
	}
	 
	
	
	@Override
    public String toString() {
        return "Shelf{" +
               "shelfId=" + shelfId +
               ", shelfName='" + shelfName + '\'' +
               ", roomId=" + roomId +
               ", capacity=" + capacity +
               ", shelfCategory='" + shelfCategory + '\'' +
               ", currentBookCount=" + currentBookCount +
               ", status='" + status + '\'' +
               '}';
    }
	
	
	
	
	
	
	

	    public int getShelfCapacity() {
	        return shelfCapacity;
	    }

	   
}