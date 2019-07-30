package com.trilogyed.Bookservice.util.message;

import com.trilogyed.Bookservice.model.Note;

import java.util.List;

public class NoteListEntry {
    private Integer bookId;
    private Integer noteId;
    private String note;

    public NoteListEntry(){}

    public NoteListEntry(Integer bookId, Integer noteId, String note){
       this.bookId=bookId;
       this.noteId=noteId;
       this.note=note;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

//    @Override
//    public String toString() {
//        return "BookViewModel{" +
//                "bookId=" + bookId +
//                ", author='" + author + '\'' +
//                ", title='" + title + '\'' +
//                ", noteList=" + noteList +
//                '}';
//    }
}
