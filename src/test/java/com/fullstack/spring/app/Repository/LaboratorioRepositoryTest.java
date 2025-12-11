package com.fullstack.spring.app.Repository;

import com.fullstack.spring.app.Model.Laboratorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LaboratorioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Test
    void deberiaEncontrarLaboratoriosActivos() {
        Laboratorio lab1 = new Laboratorio();
        lab1.setNombre("Lab Activo");
        lab1.setActivo(true);
        entityManager.persist(lab1);

        Laboratorio lab2 = new Laboratorio();
        lab2.setNombre("Lab Inactivo");
        lab2.setActivo(false);
        entityManager.persist(lab2);

        entityManager.flush();

        List<Laboratorio> activos = laboratorioRepository.findByActivoTrue();

        assertThat(activos).hasSize(1);
        assertThat(activos.get(0).getNombre()).isEqualTo("Lab Activo");
    }

    @Test
    void deberiaEncontrarLaboratoriosPorEspecialidad() {
        Laboratorio lab1 = new Laboratorio();
        lab1.setNombre("Lab 1");
        lab1.setEspecialidad("Cardiología");
        entityManager.persist(lab1);

        Laboratorio lab2 = new Laboratorio();
        lab2.setNombre("Lab 2");
        lab2.setEspecialidad("Cardiología");
        entityManager.persist(lab2);

        Laboratorio lab3 = new Laboratorio();
        lab3.setNombre("Lab 3");
        lab3.setEspecialidad("Neurología");
        entityManager.persist(lab3);

        entityManager.flush();

        List<Laboratorio> cardio = laboratorioRepository.findByEspecialidad("Cardiología");

        assertThat(cardio).hasSize(2);
        assertThat(cardio).extracting(Laboratorio::getEspecialidad)
                .containsOnly("Cardiología");
    }

    @Test
    void deberiaGuardarLaboratorio() {
        Laboratorio lab = new Laboratorio();
        lab.setNombre("Nuevo Lab");
        lab.setActivo(true);

        Laboratorio guardado = laboratorioRepository.save(lab);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Nuevo Lab");
    }

    @Test
    void deberiaEliminarLaboratorio() {
        Laboratorio lab = new Laboratorio();
        lab.setNombre("Lab a eliminar");
        entityManager.persist(lab);
        entityManager.flush();

        Long id = lab.getId();

        laboratorioRepository.deleteById(id);

        assertThat(laboratorioRepository.findById(id)).isEmpty();
    }
}