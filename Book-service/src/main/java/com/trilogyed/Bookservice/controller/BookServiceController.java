package com.trilogyed.Bookservice.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import com.trilogyed.Bookservice.model.Book;
import com.trilogyed.Bookservice.service.BookService;
import com.trilogyed.Bookservice.viewmodel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookServiceController {

    @Autowired
    BookService bookService;
    //CRUD and get all controller
//    Create Book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookViewModel createBook(@RequestBody @Valid BookViewModel bookViewModel){
        return bookService.newBook(bookViewModel);
    }

//    Get Book
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookViewModel getBook(@PathVariable("id") int id){
        BookViewModel bookViewModel = bookService.fetchBook(id);
        //exception handling if necessary
        return bookViewModel;
    }

//    Get All Books
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookViewModel> getAllBooks(){
        List<BookViewModel> bookViewModelList = bookService.fetchAllBooks();
        //exception handling if necessary
        return bookViewModelList;
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
