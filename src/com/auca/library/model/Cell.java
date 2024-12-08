package com.auca.library.model;

import javax.persistence.*;

@Entity
@Table(name = "cells")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    public Cell() {}

    public Cell(String name, Sector sector) {
        this.name = name;
        this.sector = sector;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
}