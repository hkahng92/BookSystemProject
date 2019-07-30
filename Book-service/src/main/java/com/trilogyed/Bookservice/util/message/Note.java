package com.trilogyed.Bookservice.util.message;

import java.util.Objects;

public class Note {
    private Integer bookId;
    private Integer noteId;
    private String note;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return Objects.equals(bookId, note1.bookId) &&
                Objects.equals(noteId, note1.noteId) &&
                Objects.equals(note, note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, noteId, note);
    }
}
