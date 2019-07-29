package com.trilogyed.Bookservice.dao;

import com.trilogyed.Bookservice.model.Book;

import java.util.List;

public interface BookDao {
    Book createBook(Book book);

    Book getBookById(int id);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void deleteBookById(int id);
}
