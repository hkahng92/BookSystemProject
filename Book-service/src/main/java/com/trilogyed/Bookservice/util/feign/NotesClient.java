package com.trilogyed.Bookservice.util.feign;

import com.trilogyed.Bookservice.util.message.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(name = "note-service")
public interface NotesClient {

        @RequestMapping(value = "/consume", method = RequestMethod.POST)
        public List<Note> getNotesWithId();

        @RequestMapping(value = "/update", method = RequestMethod.PUT)
        public List<Note> updateNoteFromBook();

        @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
        public List<Note> getNotesByBookId(int id);

        @RequestMapping(value = "notes/{id}", method = RequestMethod.DELETE)
        public void deleteNote(int noteId);
}
