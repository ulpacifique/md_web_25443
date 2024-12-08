package com.auca.library.model;

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

// Getters and Setters
// Constructors
// Utility Methods
