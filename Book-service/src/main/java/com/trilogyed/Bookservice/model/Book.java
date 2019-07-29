package com.trilogyed.Bookservice.model;

import java.util.Objects;

public class Book {
    private int bookId;
    private String author;
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return bookId == book.bookId &&
                author.equals(book.author) &&
                title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, author, title);
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book() {
    }

    public Book(int bookId, String author, String title) {
        this.bookId = bookId;
        this.author = author;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
