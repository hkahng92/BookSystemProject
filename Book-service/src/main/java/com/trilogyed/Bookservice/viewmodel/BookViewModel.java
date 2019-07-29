package com.trilogyed.Bookservice.viewmodel;

import com.trilogyed.Bookservice.model.Note;

import java.util.List;
import java.util.Objects;

public class BookViewModel {
    private int bookId;
    private String author;
    private String title;
    private List<Note> noteList;

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

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public BookViewModel() {
    }

    public BookViewModel(int bookId, String author, String title, List<Note> noteList) {
        this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.noteList = noteList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookViewModel)) return false;
        BookViewModel that = (BookViewModel) o;
        return getBookId() == that.getBookId() &&
                getAuthor().equals(that.getAuthor()) &&
                getTitle().equals(that.getTitle()) &&
                getNoteList().equals(that.getNoteList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getAuthor(), getTitle(), getNoteList());
    }

    @Override
    public String toString() {
        return "BookViewModel{" +
                "bookId=" + bookId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", noteList=" + noteList +
                '}';
    }
}
