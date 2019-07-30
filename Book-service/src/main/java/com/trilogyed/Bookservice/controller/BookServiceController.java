package com.trilogyed.Bookservice.controller;

import com.trilogyed.Bookservice.service.BookService;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookServiceController {

    @Autowired
    BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookViewModel createBook(@RequestBody BookViewModel book){
        return bookService.newBook(book);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookViewModel getBook(@PathVariable("id") int id){
        BookViewModel book = bookService.fetchBook(id);
        if(book == null)
            throw new IllegalArgumentException("Book could not be retrieved for id " + id);
        return book;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookViewModel> getAllBooks(){
        return bookService.fetchAllBooks();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("id") int bookId, @RequestBody @Valid BookViewModel bookViewModel){
        if(bookViewModel.getBookId()==0)
            bookViewModel.setBookId(bookId);
        if ((bookId != bookViewModel.getBookId())){
            throw new IllegalArgumentException("Book ID on path must match the ID in the Book object");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") int bookId){
        bookService.deleteBook(bookId);
    }

//    Update Book


//
//    Delete Book
//===========
//    URI: /books/{id}
//    HTTP Method: DELETE
//    RequestBody: None
//    ResponseBody: None
}
