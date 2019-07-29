package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookService {

    BookDao bookDao;

    @Autowired
    public BookService(BookDao bookDao){
        this.bookDao=bookDao;
    }

    public BookViewModel fetchBook(int id){
        return null;
    }
    public List<BookViewModel> fetchAllBooks(){
        return null;
    }
    public BookViewModel newBook(BookViewModel bookViewModel){
        return null;
    }
    public void deleteBook(int id){

    }
    public BookViewModel updateBook(BookViewModel bookViewModel){
        return null;
    }

    private BookViewModel buildBookViewModel(Book book){
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setBookId(book.getBookId());
        bookViewModel.setAuthor(book.getAuthor());
        bookViewModel.setTitle(book.getTitle());
        //there needs to be List<Note> in here
        //List<Note> notes = noteDao.getAllNotes();
        //bookViewModel.setNotes(notes);

        return bookViewModel;

    }
}
