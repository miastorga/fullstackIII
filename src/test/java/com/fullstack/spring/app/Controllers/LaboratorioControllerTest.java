package com.fullstack.spring.app.Controllers;

import com.fullstack.spring.app.Model.Laboratorio;
import com.fullstack.spring.app.Service.LaboratorioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LaboratorioController.class)
class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LaboratorioService laboratorioService;

    @Test
    void deberiaListarTodosLosLaboratorios() throws Exception {
        Laboratorio lab1 = new Laboratorio();
        lab1.setId(1L);
        lab1.setNombre("Lab 1");

        when(laboratorioService.listarTodos()).thenReturn(Arrays.asList(lab1));

        mockMvc.perform(get("/api/laboratorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Lab 1"));
    }

    @Test
    void deberiaBuscarLaboratorioPorId() throws Exception {
        Laboratorio lab = new Laboratorio();
        lab.setId(1L);
        lab.setNombre("Lab 1");

        when(laboratorioService.buscarPorId(1L)).thenReturn(Optional.of(lab));

        mockMvc.perform(get("/api/laboratorios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Lab 1"));
    }

    @Test
    void deberiaRetornar404CuandoLaboratorioNoExiste() throws Exception {
        when(laboratorioService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/laboratorios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaCrearLaboratorio() throws Exception {
        Laboratorio lab = new Laboratorio();
        lab.setId(1L);
        lab.setNombre("Nuevo Lab");

        when(laboratorioService.crear(any(Laboratorio.class))).thenReturn(lab);

        mockMvc.perform(post("/api/laboratorios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Nuevo Lab\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Nuevo Lab"));
    }

    @Test
    void deberiaActualizarLaboratorio() throws Exception {
        Laboratorio lab = new Laboratorio();
        lab.setId(1L);
        lab.setNombre("Lab Actualizado");

        when(laboratorioService.actualizar(eq(1L), any(Laboratorio.class))).thenReturn(lab);

        mockMvc.perform(put("/api/laboratorios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Lab Actualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Lab Actualizado"));
    }

    @Test
    void deberiaEliminarLaboratorio() throws Exception {
        when(laboratorioService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/laboratorios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Laboratorio eliminado"));
    }
}