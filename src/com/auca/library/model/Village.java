package com.auca.library.model;

import javax.persistence.*;

@Entity
@Table(name = "villages")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id", nullable = false)
    private Cell cell;

    public Village() {}

    public Village(String name, Cell cell) {
        this.name = name;
        this.cell = cell;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Cell getCell() { return cell; }
    public void setCell(Cell cell) { this.cell = cell; }
}