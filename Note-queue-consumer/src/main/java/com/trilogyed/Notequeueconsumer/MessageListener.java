package com.trilogyed.Notequeueconsumer;

import com.trilogyed.Notequeueconsumer.util.feign.NotesClient;
import com.trilogyed.Notequeueconsumer.util.messages.Note;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageListener {


    @Autowired
    private NotesClient client;

    public MessageListener(NotesClient client) {
        this.client = client;
    }
    @SendTo
    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public  List<Note> recieveMessage(List<Note> msg){

       // System.out.println(msg.toString());

        //System.out.println(msg.toString());

        if(msg.get(0).getNoteId() == null){
            msg.stream()
                    .forEach(note -> {
                        note = client.createNote(note);
                    });
            for(Note n : msg) {
                System.out.println("note created " + n.toString());
            }
            return msg;
        }else{
            msg.stream()
                    .forEach(note -> {
                        client.updateNote(note, note.getNoteId());
                    });
            List<Note> messageNote = new ArrayList<>();
            for(Note n : msg) {
                System.out.println("note updated " + n.toString());
            }
            return msg;
        }

    }




}
