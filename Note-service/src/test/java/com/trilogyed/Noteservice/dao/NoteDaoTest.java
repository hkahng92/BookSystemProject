package com.trilogyed.Noteservice.dao;

import com.trilogyed.Noteservice.model.Note;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoTest {

    @Autowired
     NoteDao dao;

    @Before
    public void setUp() throws Exception {
        List<Note> noteList = dao.getAllNotes();
        for(Note n : noteList){
            dao.deleteNote(n.getNoteId());
        }
    }

    @After
    public void tearDown() throws Exception {
        List<Note> noteList = dao.getAllNotes();
        for(Note n : noteList){
            dao.deleteNote(n.getNoteId());
        }
    }

    @Test
    public void createGetAndDeleteNote() {
      Note note = new Note();
      note.setNote("I have 10 books");
      note.setBookId(23);

      note = dao.createNote(note);

      Note note1 = dao.getNoteById(note.getNoteId());

      assertEquals(note, note1);

      dao.deleteNote(note.getNoteId());

      note1 = dao.getNoteById(note.getNoteId());

      assertNull(note1);

    }

    @Test
    public void getNotesByBook() {
        Note note = new Note();
        note.setNote("I have 10 books");
        note.setBookId(23);

        note = dao.createNote(note);

        Note note1 = new Note();
        note1.setNote("I have 20 books");
        note1.setBookId(23);

        note = dao.createNote(note);

        List<Note> noteList = dao.getNotesByBook(note1.getBookId());

        assertEquals(noteList.size(), 2);
    }

    @Test
    public void getAllNotes() {
        Note note = new Note();
        note.setNote("I have 10 books");
        note.setBookId(23);

        note = dao.createNote(note);

        Note note1 = new Note();
        note1.setNote("I have 20 books");
        note1.setBookId(23);

        note = dao.createNote(note);

        List<Note> noteList = dao.getAllNotes();

        assertEquals(noteList.size(), 2);
    }

    @Test
    public void updateNote() {

        Note note = new Note();
        note.setNote("I have 10 books");
        note.setBookId(23);

        note = dao.createNote(note);

        note.setBookId(25);
        dao.updateNote(note);

        Note note1 = dao.getNoteById(note.getNoteId());

        assertEquals(note, note1);
    }
}