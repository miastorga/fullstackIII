package com.fullstack.spring.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fullstack.spring.app.Model.Laboratorio;
import java.util.List;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    List<Laboratorio> findByActivoTrue();
    List<Laboratorio> findByEspecialidad(String especialidad);
}