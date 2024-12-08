package com.auca.library.model;

import java.sql.Timestamp;
import java.util.List;

public class Room {
    private int roomId;
    private String roomName;
    private int buildingId;
    private RoomType roomType = RoomType.LIBRARY; // Default value
    private String roomNumber = ""; // Default empty string
    private int capacity;
    private int floorNumber = 1; // Default value
    private boolean isActive = true; // Default value
    private String description = ""; // Default empty string
    private int bookCount = 0;
    private List<Shelf> shelves;
    private double shelfOccupancyPercentage;

    // Enum remains the same
    public enum RoomType {
        LIBRARY("Library"),
        STUDY("Study Room"),
        STORAGE("Storage"),
        OFFICE("Office"),
        CLASSROOM("Classroom");

   

        private final String displayName;

        RoomType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Getters
    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getBuildingId() {
        return buildingId;
    }

   
    public RoomType getRoomType() {
        return roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getDescription() {
        return description;
    }



    // Setters
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = (roomName != null) ? roomName.trim() : "";
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

   

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setDescription(String description) {
        this.description = (description != null) ? description.trim() : "";
    }

    public int getBookCount() {
        return bookCount;
    }

    
	public void setBookCount(int countBooksInRoom) {
		// TODO Auto-generated method stub
		
	}
	
	 public List<Shelf> getShelves() {
	        return shelves;
	    }

	    public void setShelves(List<Shelf> shelves) {
	        this.shelves = shelves;
	    }

	    public double getShelfOccupancyPercentage() {
	        return shelfOccupancyPercentage;
	    }

	    public void setShelfOccupancyPercentage(double shelfOccupancyPercentage) {
	        this.shelfOccupancyPercentage = shelfOccupancyPercentage;
	    }

 
}