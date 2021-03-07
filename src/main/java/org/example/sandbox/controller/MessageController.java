package org.example.sandbox.controller;

import org.example.sandbox.controller.dto.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(
        value = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MessageController {

    @PostMapping("publish")
    public void publish(@RequestBody Message message) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("getLast")
    public Message getLast() {
        return null;
    }

    @GetMapping("getByTime")
    public List<Message> getByTime(
            @RequestParam long start,
            @RequestParam long end
    ) {
        return Collections.emptyList();
    }
}
