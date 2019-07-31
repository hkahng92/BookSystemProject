package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.dao.BookDaoJdbcTemplateImpl;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.util.feign.NoteClientFeignImpl;
import com.trilogyed.Bookservice.util.feign.NotesClient;
import com.trilogyed.Bookservice.util.message.Note;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

    //@Autowired
    BookDao bookDao;

    //@Autowired
    BookService service;

    //@Autowired
    NotesClient client;

    //@Autowired
    RabbitTemplate template;

    @Autowired
    NotesClient notesClient;

    @Before
    public void setUp() throws Exception {
        setUpBookDaoMock();
        setUpNoteClient();
        template = mock(RabbitTemplate.class);

        service = new BookService(template, bookDao, client);
    }

    private void setUpBookDaoMock(){
        //template = mock(RabbitTemplate.class);
        bookDao = mock(BookDaoJdbcTemplateImpl.class);

        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Title One");
        book.setAuthor("Author One");

        Book book1 = new Book();

        book1.setTitle("Title One");
        book1.setAuthor("Author One");

//        Book bookUpdate = new Book();
//        bookUpdate.setBookId(1);
//        bookUpdate.setTitle("Title TWO");
//        bookUpdate.setAuthor("Author TWO");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        doReturn(book).when(bookDao).createBook(book1);
        doReturn(book).when(bookDao).getBookById(1);
        doReturn(bookList).when(bookDao).getAllBooks();
        //doReturn(bookUpdate).when(bookDao).updateBook(book);
    }

    @Test
    public void newFetchBook() throws InterruptedException {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title One");
        bookViewModel.setAuthor("Author One");
        //for the notes

        bookViewModel = service.newBook(bookViewModel);

        BookViewModel fromService = service.fetchBook(bookViewModel.getBookId());
        assertEquals(bookViewModel,fromService);

    }

    @Test
    public void fetchAllBooks() throws InterruptedException {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title One");
        bookViewModel.setAuthor("Author One");
        //for the notes

        service.newBook(bookViewModel);

        List<BookViewModel> bookViewModels = service.fetchAllBooks();

        assertEquals(1,bookViewModels.size());
    }

//    @Test
//    public void deleteBook() throws InterruptedException {
//        BookViewModel bookViewModel = new BookViewModel();
//        bookViewModel.setTitle("Title One");
//        bookViewModel.setAuthor("Author One");
//
//        service.newBook(bookViewModel);
//
//        service.deleteBook(bookViewModel.getBookId());
//
//        BookViewModel bookViewModel1 = service.fetchBook(bookViewModel.getBookId());
//        assertNull(bookViewModel1);
//    }
//
//    @Test
//    public void updateBook() throws InterruptedException {
//        BookViewModel bookViewModel = new BookViewModel();
//        bookViewModel.setBookId(1);
//        bookViewModel.setTitle("Title TWO");
//        bookViewModel.setAuthor("Author TWO");
//    }

    private void setUpNoteClient(){
        notesClient = mock(NoteClientFeignImpl.class);
        Note noteMock = new Note();
        noteMock.setBookId(1);
        noteMock.setNote("Take two");
        noteMock.setNoteId(25);

        Note note = new Note();
        note.setBookId(1);
        note.setNote("Take two");

        List<Note> noteList = new ArrayList<>();
        noteList.add(noteMock);

        doReturn(noteMock).when(notesClient).createNote(note);
        doReturn(noteList).when(notesClient).getNotesByBookId(1);
    }
//    @Test
//    public void deleteBook() {
//        BookViewModel bookViewModel = new BookViewModel();
//        bookViewModel.setTitle("Title One");
//        bookViewModel.setAuthor("Author One");
//
//        service.newBook(bookViewModel);
//
//        service.deleteBook(bookViewModel.getBookId());
//
//        BookViewModel bookViewModel1 = service.fetchBook(bookViewModel.getBookId());
//        assertNull(bookViewModel1);
//    }
//    @Test
//    public void updateBook() {
//        BookViewModel bookViewModel = new BookViewModel();
//        bookViewModel.setBookId(1);
//        bookViewModel.setTitle("Title TWO");
//        bookViewModel.setAuthor("Author TWO");
//
//        BookViewModel fromService = service.updateBook(bookViewModel);
//        assertEquals(bookViewModel,fromService);



}