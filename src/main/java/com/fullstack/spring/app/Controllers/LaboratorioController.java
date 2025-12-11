package com.fullstack.spring.app.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.spring.app.Model.Laboratorio;
import com.fullstack.spring.app.Service.LaboratorioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laboratorios")
@CrossOrigin(origins = "*")
public class LaboratorioController {
    
    @Autowired
    private LaboratorioService laboratorioService;
    
    @GetMapping
    public ResponseEntity<List<Laboratorio>> listarTodos() {
        return ResponseEntity.ok(laboratorioService.listarTodos());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<Laboratorio>> listarActivos() {
        return ResponseEntity.ok(laboratorioService.listarActivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Laboratorio> buscarPorId(@PathVariable Long id) {
        Optional<Laboratorio> lab = laboratorioService.buscarPorId(id);
        return lab.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Laboratorio> crear(@RequestBody Laboratorio laboratorio) {
        Laboratorio nuevo = laboratorioService.crear(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> actualizar(@PathVariable Long id, @RequestBody Laboratorio lab) {
        Laboratorio actualizado = laboratorioService.actualizar(id, lab);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        boolean resultado = laboratorioService.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok("Laboratorio eliminado");
        }
        return ResponseEntity.notFound().build();
    }
}