package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.util.message.Note;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

import java.util.List;

@Component
public class BookService {
    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.add.book.service";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private BookDao bookDao;

    @Autowired
    public BookService (RabbitTemplate rabbitTemplate, BookDao bookDao){
        this.rabbitTemplate=rabbitTemplate;
        this.bookDao=bookDao;
    }


    public BookViewModel fetchBook(int id) {
        Book book = bookDao.getBookById(id);
        if(book ==  null)
            return null;
        else
            return buildBookViewModel(book);

    }

    public List<BookViewModel> fetchAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> ViewModelList = new ArrayList<>();

        bookList.stream()
                .forEach(b -> {
                    BookViewModel bvm = buildBookViewModel(b);
                    ViewModelList.add(bvm);
                });
        return ViewModelList;

    }

    @Transactional
    public BookViewModel newBook(BookViewModel bookViewModel) {
        Book book = new Book();

        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book = bookDao.createBook(book);
        bookViewModel.setBookId(book.getBookId());

        List<Note> noteList = bookViewModel.getNoteList();

        System.out.println("Sending message to the queue consumer...");
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,noteList);
        System.out.println("Message Sent.");

        //bookViewModel.setNoteList(noteList);

        return bookViewModel;
    }

    public void deleteBook(int id) {
        bookDao.deleteBookById(id);

    }

    public BookViewModel updateBook(BookViewModel bookViewModel) {
        Book book = new Book();

        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book.setBookId(bookViewModel.getBookId());
        bookDao.updateBook(book);

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
