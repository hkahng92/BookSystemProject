package com.trilogyed.Notequeueconsumer.util.feign;

import com.trilogyed.Notequeueconsumer.util.messages.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "note-service")
public interface NotesClient {
// to be removed
//    @RequestMapping(value = "/consume", method = RequestMethod.POST)
//    public List<Note> getNotesWithId();
//
//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
//    public void updateNoteFromBook();

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    public Note getNote(@PathVariable(value = "id") int noteId);

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    public List<Note> getNotesByBookId(@PathVariable(value = "book_id") int id);

    @RequestMapping(value = "notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable(value = "id") int noteId);

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public Note createNote(@RequestBody Note note);

    @RequestMapping(value = "notes/{id}", method = RequestMethod.PUT)
    public void updateNote(@RequestBody Note note,
                           @PathVariable(value = "id") int noteId);

}

