package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.dao.BookDaoJdbcTemplateImpl;
import com.trilogyed.Bookservice.model.Book;


import com.trilogyed.Bookservice.util.feign.NotesClient;
import com.trilogyed.Bookservice.util.message.Note;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import feign.Feign;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class BookServiceTest {


    BookDao bookDao;

    BookService service;


    NotesClient client;

    RabbitTemplate template;


    @Before
    public void setUp() throws Exception {
     setUpBookDaoMock();
     setUpNoteClient();
     template = mock(RabbitTemplate.class);

     service = new BookService(template, bookDao, client);
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


        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        doReturn(book).when(bookDao).createBook(book1);
        doReturn(book).when(bookDao).getBookById(1);
        doReturn(bookList).when(bookDao).getAllBooks();

    }

    @Test
    public void newFetchBook() throws InterruptedException {
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title One");
        bookViewModel.setAuthor("Author One");

        //for the notes
        Note note = new Note();
        //note.setBookId(1);
        note.setNote("Take two");

        List<Note> notes = new ArrayList<>();
        notes.add(note);

        bookViewModel.setNoteList(notes);
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

    public void setUpNoteClient(){
        client = Mockito.mock(NotesClient.class);
        Note noteMock = new Note();
        noteMock.setBookId(1);
        noteMock.setNote("Take two");
        noteMock.setNoteId(25);

        Note note = new Note();

        note.setNote("Take two");
        note.setBookId(1);
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteMock);

        Mockito.when(client.createNote(note)).thenReturn(noteMock);
        //doReturn(noteMock).when(client).createNote(note);
       // doReturn(noteList).when(client).getNotesByBookId(1);
        Mockito.when(client.getNotesByBookId(1)).thenReturn(noteList);
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