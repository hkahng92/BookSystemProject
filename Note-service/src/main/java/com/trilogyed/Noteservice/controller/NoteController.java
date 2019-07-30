package com.trilogyed.Noteservice.controller;

import com.trilogyed.Noteservice.dao.NoteDao;
import com.trilogyed.Noteservice.model.Note;
import com.trilogyed.Noteservice.util.feign.NoteQueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class NoteController {

    @Autowired
    NoteDao dao;

    @Autowired
    private final NoteQueueClient noteClient;

    public NoteController(NoteQueueClient noteClient) {
        this.noteClient = noteClient;
    }

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    public List<Note> getNotesWithId(){
        List<Note> noteList = noteClient.sendToCreateNote();
        for (Note note: noteList){
            note = dao.createNote(note);
        }
        return noteList;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateNoteFromBook(){
        List<Note> noteList = noteClient.sendToUpdateNote();
        for(Note note: noteList){
            dao.updateNote(note);
        }
    }
}
