package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.dao.BookDaoJdbcTemplateImpl;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.model.Note;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookServiceTest {

    BookDao bookDao;
    BookService service;

    @Before
    public void setUp() throws Exception {
        setUpBookDaoMock();

        service=new BookService(bookDao);
    }

    private void setUpBookDaoMock(){
        bookDao = mock(BookDaoJdbcTemplateImpl.class);

        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Title One");
        book.setAuthor("Author One");

        Book book1 = new Book();

        book1.setTitle("Title One");
        book1.setAuthor("Author One");

        Book bookUpdate = new Book();
        bookUpdate.setBookId(1);
        bookUpdate.setTitle("Title TWO");
        bookUpdate.setAuthor("Author TWO");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        doReturn(book).when(bookDao).createBook(book1);
        doReturn(book).when(bookDao).getBookById(1);
        doReturn(bookList).when(bookDao).getAllBooks();
        doReturn(bookUpdate).when(bookDao).updateBook(book);
    }

    @Test
    public void newFetchBook() {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title One");
        bookViewModel.setAuthor("Author One");
        //for the notes

        bookViewModel = service.newBook(bookViewModel);

        BookViewModel fromService = service.fetchBook(bookViewModel.getBookId());
        assertEquals(bookViewModel,fromService);

    }

    @Test
    public void fetchAllBooks() {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title One");
        bookViewModel.setAuthor("Author One");
        //for the notes

        service.newBook(bookViewModel);

        List<BookViewModel> bookViewModels = service.fetchAllBooks();

        assertEquals(1,bookViewModels.size());
    }

//    @Test
//    public void newBook() {
//    }

    @Test
    public void deleteBook() {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title One");
        bookViewModel.setAuthor("Author One");

        service.newBook(bookViewModel);

        service.deleteBook(bookViewModel.getBookId());

        BookViewModel bookViewModel1 = service.fetchBook(bookViewModel.getBookId());
        assertNull(bookViewModel1);
    }

    @Test
    public void updateBook() {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setBookId(1);
        bookViewModel.setTitle("Title TWO");
        bookViewModel.setAuthor("Author TWO");

        BookViewModel fromService = service.updateBook(bookViewModel);
        assertEquals(bookViewModel,fromService);
    }


}