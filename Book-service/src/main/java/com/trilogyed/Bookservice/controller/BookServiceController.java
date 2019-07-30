package com.trilogyed.Bookservice.controller;

import com.trilogyed.Bookservice.util.feign.NotesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class BookServiceController {

@Autowired
    private final NotesClient client;

      BookServiceController(NotesClient client){
          this.client = client;
      }

     @RequestMapping(value ="/Notelist",method = RequestMethod.GET)
    public String bookCloud(){
          return client.getNote();
     }
    //CRUD and get all controller
}
