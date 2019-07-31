package com.trilogyed.Notequeueconsumer.util.messages;

import java.util.Objects;

public class Note {
    private Integer noteId;
    private Integer bookId;
    private String note;

    public Note() {
    }

    public Note(Integer noteId, Integer bookId, String note) {
        this.noteId = noteId;
        this.bookId = bookId;
        this.note = note;
    }

    public Note(Integer bookId, String note) {
        this.bookId = bookId;
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note1 = (Note) o;
        return getNoteId().equals(note1.getNoteId()) &&
                getBookId().equals(note1.getBookId()) &&
                getNote().equals(note1.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, bookId, note);
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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
}
