package org.example.sandbox.controller;

import lombok.AllArgsConstructor;
import org.example.sandbox.controller.dto.MessageDto;
import org.example.sandbox.service.MessageService;
import org.example.sandbox.service.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @PostMapping("publish")
    public void publish(@RequestBody MessageDto message) {
        messageService.publish(message.getContent());
    }

    @GetMapping("getLast")
    public MessageDto getLast() {
        Message message = messageService.getLast();
        // TODO: use converter
        return new MessageDto(message.getContent());
    }

    @GetMapping("getByTime")
    public List<MessageDto> getByTime(
            @RequestParam long start,
            @RequestParam long end
    ) {
        List<Message> messages = messageService.getByTime(start, end);
        // TODO: use converter
        return messages.stream()
                .map(it -> new MessageDto(it.getContent()))
                .collect(Collectors.toList());
    }
}
