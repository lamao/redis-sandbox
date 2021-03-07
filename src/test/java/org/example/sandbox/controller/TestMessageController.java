package org.example.sandbox.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class TestMessageController {

    @Autowired
    private MockMvc mvc;


    @Test
    public void testPublish() throws Exception {
        mvc.perform(post("/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"any-message\"}")
        )
                .andExpect(status().isOk());

    }

    @Test
    public void testGetLast() throws Exception {
        mvc.perform(get("/getLast")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"content\":\"any-message\"}"));

    }

    @Test
    public void testGetByTime() throws Exception {
        mvc.perform(get("/getByTime")
                .param("start", "10")
                .param("end", "20")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("" +
                        "[" +
                        "   {\"content\":\"one\"}," +
                        "   {\"content\":\"two\"}" +
                        "]"
                ));

    }
}
