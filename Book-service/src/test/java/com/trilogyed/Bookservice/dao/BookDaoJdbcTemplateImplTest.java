package com.trilogyed.Bookservice.dao;

import com.trilogyed.Bookservice.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoJdbcTemplateImplTest {

    @Autowired
    protected BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        List<Book> bookList = bookDao.getAllBooks();
        bookList.stream()
                .forEach(book -> bookDao.deleteBookById(book.getBookId()));
    }

    @Test
    public void createGetDeleteBook() {
        Book book = new Book();
        book.setTitle("Book 1");
        book.setAuthor("Author one");

        book = bookDao.createBook(book);

        Book book1 = bookDao.getBookById(book.getBookId());
        assertEquals(book,book1);

        bookDao.deleteBookById(book1.getBookId());
        book1 = bookDao.getBookById(book.getBookId());
        assertNull(book1);
    }

//    @Test
//    public void getBookById() {
//    }

    @Test
    public void getAllBooks() {
        Book book = new Book();
        book.setTitle("Book 1");
        book.setAuthor("Author one");

        bookDao.createBook(book);

        book = new Book();
        book.setTitle("Book 2");
        book.setAuthor("Author Two");

        bookDao.createBook(book);

        List<Book> books = bookDao.getAllBooks();
        assertEquals(2,books.size());
    }

    @Test
    public void updateBook() {
        Book book = new Book();
        book.setTitle("Book 1");
        book.setAuthor("Author one");

        book = bookDao.createBook(book);

        book.setTitle("Book 2");
        book.setAuthor("Author Two");

        bookDao.updateBook(book);

        Book book1 = bookDao.getBookById(book.getBookId());
        assertEquals(book,book1);
    }

//    @Test
//    public void deleteBookById() {
//    }
}