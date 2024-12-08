package com.auca.library.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private Date publicationDate;
    private String isbn;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int shelfId;
    private int totalCopies;
    private int availableCopies;
    private String category;
    private String description;
    private int borrowedBy; // ID of the member who borrowed the book
    private Date dueDate; // Due date for returning the book
    private Date borrowDate;
    private int borrowedBookId;
    private double fineAmount;

    // Existing getters and setters...

    // Add getter and setter for borrowedBookId
    public int getBorrowedBookId() {
        return borrowedBookId;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

	public static void setMembershipType(Object membership_type) {
		// TODO Auto-generated method stub
		
	}

	public void setBorrowDate(Date date) {
		// TODO Auto-generated method stub
		
	}
	
	
	 public Date getBorrowDate() {
	        return borrowDate;
	    }


	    public void setBorrowedBookId(int borrowedBookId) {
	        this.borrowedBookId = borrowedBookId;
	    }

	    // Getter and setter for fineAmount
	    public double getFineAmount() {
	        return fineAmount;
	    }

	    public void setFineAmount(double fineAmount) {
	        this.fineAmount = fineAmount;
	    }
 
	   
	    
}
