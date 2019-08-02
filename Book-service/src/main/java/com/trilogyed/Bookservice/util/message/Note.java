package com.trilogyed.Bookservice.util.message;

import java.util.Objects;

public class Note {

    private Integer noteId;
    private String note;
    private Integer bookId;

    public Note(String note, Integer bookId) {
        this.note = note;
        this.bookId = bookId;
    }

    public Note(){}

    public Note(Integer bookId, Integer noteId, String note){
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

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", bookId=" + bookId +
                ", note='" + note + '\'' +
                '}';
    }

    // to be removed this was the only problem lol
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Note)) return false;
//        Note note1 = (Note) o;
//        return getNoteId().equals(note1.getNoteId()) &&
//                getNote().equals(note1.getNote()) &&
//                getBookId().equals(note1.getBookId());
//    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoteId(), getNote(), getBookId());
    }
}
