package org.example.sandbox.controller;

import org.example.sandbox.service.MessageService;
import org.example.sandbox.service.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class TestMessageDtoController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;


    @Test
    public void testPublish() throws Exception {
        mvc.perform(post("/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"any-message\"}")
        )
                .andExpect(status().isOk());

    }

    @Test
    public void testGetLastWithData() throws Exception {
        Optional<Message> mockMessage = Optional.of(new Message("any-message"));
        when(messageService.getLast()).thenReturn(mockMessage);


        mvc.perform(get("/getLast")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":\"any-message\"}"));

    }

    @Test
    public void testGetLastNoData() throws Exception {
        Optional<Message> mockMessage = Optional.empty();
        when(messageService.getLast()).thenReturn(mockMessage);


        mvc.perform(get("/getLast")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetByTime() throws Exception {
        List<Message> mockMessages = Arrays.asList(
                new Message("one"),
                new Message("two")
        );

        when(messageService.getByTime(10, 30)).thenReturn(mockMessages);

        mvc.perform(get("/getByTime")
                .param("start", "10")
                .param("end", "30")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "   {\"content\":\"one\"}," +
                        "   {\"content\":\"two\"}" +
                        "]"
                ));

    }

    @Test
    public void testGetByTimeParametersNotSet() throws Exception {
        mvc.perform(get("/getByTime")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

    }
}
