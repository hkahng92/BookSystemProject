package com.trilogyed.Notequeueconsumer.controller;

import com.trilogyed.Notequeueconsumer.MessageListener;
import com.trilogyed.Notequeueconsumer.util.messages.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NoteQueueController {

    @Autowired
    MessageListener msg;


    @RequestMapping(value = "/createNotes", method = RequestMethod.POST)
    public List<Note> sendToCreateNote(){
        List<Note> note = new ArrayList<>();
        return msg.recieveMessage(note);
    }

    @RequestMapping(value = "/updateNotes", method = RequestMethod.PUT)
    public List<Note> sendToUpdateNote(){
        List<Note> note = new ArrayList<>();
        return msg.recieveMessage(note);
    }
}
