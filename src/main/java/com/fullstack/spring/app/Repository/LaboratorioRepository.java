package com.fullstack.spring.app.Repository;
import org.springframework.stereotype.Repository;

import com.laboratorio.model.Laboratorio;

import java.util.List;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    List<Laboratorio> findByActivoTrue();
    List<Laboratorio> findByEspecialidad(String especialidad);
}