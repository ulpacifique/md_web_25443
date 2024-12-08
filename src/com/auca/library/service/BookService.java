package com.auca.library.service;

import com.auca.library.model.Book;
import com.auca.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

	public List<Book> getAllBooks() {
	    List<Book> books = new ArrayList<>();
	    String query = "SELECT * FROM books";

	    try (Connection connection = DatabaseConnection.getConnection();
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {

	        while (resultSet.next()) {
	            Book book = new Book();
	            book.setBookId(resultSet.getInt("book_id"));
	            book.setTitle(resultSet.getString("title"));
	            book.setAuthor(resultSet.getString("author"));
	            book.setPublicationDate(resultSet.getDate("publication_date"));
	            book.setIsbn(resultSet.getString("isbn"));
	            book.setStatus(resultSet.getString("status"));
	            book.setCreatedAt(resultSet.getTimestamp("created_at"));
	            book.setUpdatedAt(resultSet.getTimestamp("updated_at"));
	            book.setShelfId(resultSet.getInt("shelf_id"));
	            book.setTotalCopies(resultSet.getInt("total_copies"));
	            book.setAvailableCopies(resultSet.getInt("available_copies"));
	            book.setCategory(resultSet.getString("category"));
	            book.setDescription(resultSet.getString("description"));
	            books.add(book);
	        }

	        // Debugging: Print the list of books
	        System.out.println("Retrieved books: " + books);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return books;
	}

}
