package com.auca.library.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrowed_books")
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowed_book_id")
    private int borrowedBookId;

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "borrow_date")
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Column(name = "status")
    private String status;

    // Getters and setters
    public int getBorrowedBookId() {
        return borrowedBookId;
    }

    public void setBorrowedBookId(int borrowedBookId) {
        this.borrowedBookId = borrowedBookId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public void setFineAmount(double fineAmount) {
		// TODO Auto-generated method stub
		
	}
}