package com.auca.library.model;

public class Location {

    private int id;
    private String name;
    private String level;

    // Constructor that accepts id, name, and level
    public Location(int id, String name, String level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Location [id=" + id + ", name=" + name + ", level=" + level + "]";
    }
}
