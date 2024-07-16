package com.example.forum.controller;

import com.example.forum.model.Topico;
import com.example.forum.service.TopicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TopicoController.class)
@AutoConfigureMockMvc
public class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicoService topicoService;

    @Test
    public void getAllTopics_thenStatusOk() throws Exception {
        List<Topico> topics = new ArrayList<>();
        topics.add(new Topico(1L, "Tópico 1", "Contenido del tópico 1"));
        topics.add(new Topico(2L, "Tópico 2", "Contenido del tópico 2"));

        Mockito.when(topicoService.getAllTopics()).thenReturn(topics);

        mockMvc.perform(get("/topicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Tópico 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].titulo").value("Tópico 2"));
    }

    @Test
    public void createTopic_thenStatusCreated() throws Exception {
        Topico newTopic = new Topico(null, "Nuevo Tópico", "Contenido del nuevo tópico");

        Mockito.when(topicoService.createTopic(Mockito.any(Topico.class))).thenReturn(newTopic);

        mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTopic)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Nuevo Tópico"))
                .andExpect(jsonPath("$.contenido").value("Contenido del nuevo tópico"));
    }

    @Test
    public void deleteTopic_thenStatusOk() throws Exception {
        Long topicId = 1L;

        mockMvc.perform(delete("/topicos/{id}", topicId))
                .andExpect(status().isOk());
    }

    // Método utilitario para convertir objetos a formato JSON
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
