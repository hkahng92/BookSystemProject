package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.util.feign.NotesClient;
import com.trilogyed.Bookservice.util.message.Note;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

import java.util.List;

@Component
public class BookService {
    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.add.book.service";


    private RabbitTemplate rabbitTemplate;
    private BookDao bookDao;
    private final NotesClient client;

    @Autowired
    public BookService(RabbitTemplate rabbitTemplate, BookDao bookDao, NotesClient client){
        this.rabbitTemplate=rabbitTemplate;
        this.bookDao=bookDao;
        this.client = client;
    }


    @Transactional
    public BookViewModel fetchBook(int id) {
        Book book = bookDao.getBookById(id);
        if(book ==  null)
            return null;
        else {
            BookViewModel b = buildBookViewModel(book);
            b.setNoteList(client.getNotesByBookId(id));
            return b;
        }
    }

    @Transactional
    public List<BookViewModel> fetchAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> ViewModelList = new ArrayList<>();

        bookList.stream()
                .forEach(b -> {
                    BookViewModel bvm = buildBookViewModel(b);
                    bvm.setNoteList(client.getNotesByBookId(b.getBookId()));
                    ViewModelList.add(bvm);
                });
        return ViewModelList;

    }

    @Transactional
    public BookViewModel newBook(BookViewModel bookViewModel) throws InterruptedException {
        Book book = new Book();

        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book = bookDao.createBook(book);
        bookViewModel.setBookId(book.getBookId());

        List<Note> noteList = bookViewModel.getNoteList();
        for(Note n : noteList){
            n.setBookId(book.getBookId());
        }

        System.out.println("Sending create message to the queue consumer...");
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,noteList);
        System.out.println("create Message Sent.");

       for (Note n : noteList){
           n = client.createNote(n);
       }
       Thread.sleep(2000);
        bookViewModel.setNoteList(client.getNotesByBookId(book.getBookId()));

        return bookViewModel;
    }

    @Transactional
    public void deleteBook(int id) {
        BookViewModel b = fetchBook(id);
        for(Note n : b.getNoteList()){
            client.deleteNote(n.getNoteId());
        }

        bookDao.deleteBookById(b.getBookId());

    }


    @Transactional
    public BookViewModel updateBook(BookViewModel bookViewModel) throws InterruptedException {
        Book book = new Book();

        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book.setBookId(bookViewModel.getBookId());
        bookDao.updateBook(book);
        System.out.println("Sending update message to the queue consumer...");
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,bookViewModel.getNoteList());
        System.out.println("update Message Sent.");


       for(Note n : bookViewModel.getNoteList()){
           client.updateNote(n, n.getNoteId());
       }
       Thread.sleep(2000);
        bookViewModel.setNoteList(client.getNotesByBookId(book.getBookId()));
        return bookViewModel;
    }
    // Helper methods
    private BookViewModel buildBookViewModel (Book book){
        BookViewModel bvm = new BookViewModel();
        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());

        return bvm;

    }
}
