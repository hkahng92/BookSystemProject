package com.trilogyed.Noteservice.controller;

import com.trilogyed.Noteservice.dao.NoteDao;
import com.trilogyed.Noteservice.model.Note;
import com.trilogyed.Noteservice.util.feign.NoteQueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private List<String> greetingList = new ArrayList<>();
    // so we can randomly return a greeting
    private Random rndGenerator = new Random();

    @RequestMapping(value = "/note", method = RequestMethod.GET)
    public String getNote() {
        greetingList.add("HiYa!");
        greetingList.add("Hello!!!");
        greetingList.add("Howdy!");
        greetingList.add("Greetings!");
        greetingList.add("Hi!!!!!");
        // select and return a random greeting
        int whichGreeting = rndGenerator.nextInt(5);
        return greetingList.get(whichGreeting);
    }

}
