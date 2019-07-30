package com.trilogyed.Bookservice.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(name = "note-service")
public interface NotesClient {

        @RequestMapping(value = "/note", method = RequestMethod.GET)
        public String getNote();

}
