package com.trilogyed.Noteservice.dao;

import com.trilogyed.Noteservice.model.Note;

import java.util.List;

public interface NoteDao {
    Note createNote(Note note);

    Note getNoteById(int id);

    List<Note> getNotesByBook(int bookId);

    List<Note> getAllNotes();

    void updateNote(Note note);

    void deleteNote(int id);
}
