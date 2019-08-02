package com.trilogyed.Noteservice.controller;

import com.trilogyed.Noteservice.dao.NoteDao;
import com.trilogyed.Noteservice.exception.NotFoundException;
import com.trilogyed.Noteservice.model.Note;
import com.trilogyed.Noteservice.util.feign.NoteQueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
@RestController
public class NoteController {


    NoteDao dao;
    private final NoteQueueClient noteClient;

    @Autowired
    public NoteController(NoteDao dao, NoteQueueClient noteClient) {
        this.dao = dao;
        this.noteClient = noteClient;
    }

//to be removed
//    @RequestMapping(value = "/consume", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<Note> getNotesWithId(){
//        List<Note> noteList = noteClient.sendToCreateNote();
//        for (Note note: noteList){
//            note = dao.createNote(note);
//        }
//        return noteList;
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public List<Note> updateNoteFromBook(){
//        List<Note> noteList = noteClient.sendToUpdateNote();
//        for(Note note: noteList){
//            dao.updateNote(note);
//        }
//        return noteList;
//    }


    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody Note note){
        note = dao.createNote(note);
        return note;
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable(value = "id") int noteId){
        Note note = dao.getNoteById(noteId);
        if (note == null)
            throw new NotFoundException("note could not be retrieved for id " + noteId);
        return note;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNotes(){
        return dao.getAllNotes();
    }

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getNotesByBookId(@PathVariable(value = "book_id")int id){
        return dao.getNotesByBook(id);
    }

    @RequestMapping(value = "notes/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNote(@RequestBody @Valid Note note,
                           @PathVariable(value = "id") int noteId){

        if(note.getNoteId() == 0)
            note.setNoteId(noteId);
        if (noteId != note.getNoteId()){
            throw new IllegalArgumentException("Note ID on path must match the ID in the note Object");
        }
        dao.updateNote(note);
    }

    @RequestMapping(value = "notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable(value = "id") int noteId) {
        dao.deleteNote(noteId);

    }


}
