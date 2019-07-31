package com.trilogyed.Notequeueconsumer;

import com.trilogyed.Notequeueconsumer.util.messages.Note;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
public class MessageListener {


    public  List<Note> recieveMessage(List<Note> msg){

        System.out.println(msg.toString());

            if(msg.get(0).getNoteId() == null){
               return forCreate(msg);
            }else{
               return forUpdate(msg);
            }

    }


    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    @SendTo
    public static List<Note> forCreate(List<Note> msg){
        return msg;
    }

    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    @SendTo
    public static List<Note> forUpdate(List<Note> msg){

        return msg;
    }
    @RequestMapping(value = "/createNotes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Note> sendToCreateNote(){
        List<Note> note = new ArrayList<>();
        note = recieveMessage(note);
        System.out.println(note.toString());
        return note;
    }

    @RequestMapping(value = "/updateNotes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Note> sendToUpdateNote(){
        List<Note> note = new ArrayList<>();
        note = recieveMessage(note);
        System.out.println(note.toString());
        return note;
    }


}
