package com.trilogyed.Noteservice.util.feign;

import com.trilogyed.Noteservice.model.Note;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "note-consumer")
public interface NoteQueueClient {

    @RequestMapping(value = "/createNotes", method = RequestMethod.GET)
    public List<Note> sendToCreateNote();

    @RequestMapping(value ="/updateNotes", method = RequestMethod.GET)
    public List<Note> sendToUpdateNote();
}
