package com.trilogyed.Bookservice.dao;

import com.trilogyed.Bookservice.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoJdbcTemplateImpl implements BookDao{
    @Override
    public Book createBook(Book book) {
        return null;
    }

    @Override
    public Book getBookById(int id) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void deleteBookById(int id) {

    }
}
