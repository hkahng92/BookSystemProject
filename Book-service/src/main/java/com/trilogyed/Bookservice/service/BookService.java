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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private BookDao bookDao;


    @Autowired
    private final NotesClient client;


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
            if(client.getNotesByBookId(id) == null){
                //do nothing
            }else {

                b.setNoteList(client.getNotesByBookId(id));
            }
            return b;
        }
    }

    @Transactional
    public List<BookViewModel> fetchAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> viewModelList = new ArrayList<>();

        bookList.stream()
                .forEach(b -> {
                    BookViewModel bvm = buildBookViewModel(b);
                    if(client.getNotesByBookId(b.getBookId()) == null){
                        //do nothing
                    }else{
                        bvm.setNoteList(client.getNotesByBookId(b.getBookId()));

                    }
                    viewModelList.add(bvm);
                });

        return viewModelList;

    }

    @Transactional
    public BookViewModel newBook(BookViewModel bookViewModel) throws InterruptedException {
        Book book = new Book();

        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book = bookDao.createBook(book);

        bookViewModel.setBookId(book.getBookId());

        List<Note> noteList = bookViewModel.getNoteList();
        if(noteList == null){
            return bookViewModel;
        } else {
            List<Note> notelistWithId = new ArrayList<>();

            for (Note n : noteList) {
                n.setBookId(book.getBookId());
                notelistWithId.add(n);

            }
            bookViewModel.setNoteList(notelistWithId);

            System.out.println("Sending create message to the queue consumer...");
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, bookViewModel.getNoteList());
            System.out.println("create Message Sent.");

            //to be removed because the consumer is now creating the notes
            // a slow method process is simulated next
//            List<Note> noteList1 = new ArrayList<>();
//
//            bookViewModel.getNoteList().stream()
//                    .forEach(n -> {
//                        n = client.createNote(n);
//                        noteList1.add(n);
//                    });
//
//            bookViewModel.setNoteList(noteList1);
            Thread.sleep(2000);
            bookViewModel.setNoteList(client.getNotesByBookId(bookViewModel.getBookId()));

            return bookViewModel;
        }


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

        // to be removed queue now handles the update will optimize this later
//       for(Note n : bookViewModel.getNoteList()){
//           client.updateNote(n, n.getNoteId());
//       }
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
