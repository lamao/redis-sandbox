package org.example.sandbox.controller;

import org.example.sandbox.controller.dto.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class MessageController {

    @PostMapping(value = "publish", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void publish(@RequestBody Message message) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping(value = "getLast", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getLast() {
        return null;
    }

    @GetMapping(value = "getByTime", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getByTime(
            @RequestParam long start,
            @RequestParam long end
    ) {
        return Collections.emptyList();
    }
}
