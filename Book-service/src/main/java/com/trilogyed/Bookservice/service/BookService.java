package com.trilogyed.Bookservice.service;

import com.trilogyed.Bookservice.dao.BookDao;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {
    BookDao dao;
    @Autowired
    public BookService(BookDao dao){
        this.dao = dao;
    }
    public BookViewModel fetchBook(int id) {
        Book book = dao.getBookById(id);
        if(book ==  null)
            return null;
        else
            return buildBookViewModel(book);

    }

    public List<BookViewModel> fetchAllBooks() {
        List<Book> bookList = dao.getAllBooks();
        List<BookViewModel> ViewModelList = new ArrayList<>();

        bookList.stream()
                .forEach(b -> {
                    BookViewModel bvm = buildBookViewModel(b);
                    ViewModelList.add(bvm);
                });
        return ViewModelList;

    }

    public BookViewModel newTask(BookViewModel bookViewModel) {
        Book book = new Book();
        //TaskViewModel tvm = new TaskViewModel();
        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book = dao.createBook(book);
        bookViewModel.setBookId(book.getBookId());

        return bookViewModel;
    }

    public void deleteTask(int id) {
        dao.deleteBookById(id);

    }

    public BookViewModel updateBook(BookViewModel bookViewModel) {
        Book book = new Book();

        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        book.setBookId(bookViewModel.getBookId());
        dao.updateBook(book);

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
