package com.trilogyed.Notequeueconsumer;

import com.trilogyed.Notequeueconsumer.util.messages.Note;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageListener {

    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME) 
    public  List<Note> recieveMessage(List<Note> msg){

        System.out.println(msg.toString());

            if(msg.get(0).getNoteId() == null){
                return forCreate(msg);
            }else{
                return forUpdate(msg);
            }

    }


    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public  List<Note> forCreate(List<Note> msg){
        return msg;
    }
    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public  List<Note> forUpdate(List<Note> msg){

        return msg;
    }
}
