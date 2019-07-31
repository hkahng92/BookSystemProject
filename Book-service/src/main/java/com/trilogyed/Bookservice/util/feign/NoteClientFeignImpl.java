package com.trilogyed.Bookservice.util.feign;

import com.trilogyed.Bookservice.util.message.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteClientFeignImpl implements NotesClient{
    @Override
    public List<Note> getNotesWithId() {
        return null;
    }

    @Override
    public void updateNoteFromBook() {

    }

    @Override
    public List<Note> getNotesByBookId(int id) {
        Note noteMock = new Note();
        noteMock.setBookId(1);
        noteMock.setNote("Take two");
        noteMock.setNoteId(25);

        Note note = new Note();
        note.setBookId(1);
        note.setNote("Take two");

        List<Note> noteList = new ArrayList<>();
        noteList.add(noteMock);
        return noteList;
    }

    @Override
    public void deleteNote(int noteId) {

    }

    @Override
    public Note createNote(Note note) {
        Note noteMock = new Note();
        noteMock.setBookId(1);
        noteMock.setNote("Take two");



        return noteMock;
    }

    @Override
    public void updateNote(Note note, int noteId) {

    }
}
