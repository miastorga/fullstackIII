package com.laboratorio.service;
import com.laboratorio.model.Laboratorio;
import com.laboratorio.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {
    @Autowired
    private LaboratorioRepository laboratorioRepository;
    
    public List<Laboratorio> listarTodos() {
        return laboratorioRepository.findAll();
    }
    
    public List<Laboratorio> listarActivos() {
        return laboratorioRepository.findByActivoTrue();
    }
    
    public Optional<Laboratorio> buscarPorId(Long id) {
        return laboratorioRepository.findById(id);
    }
    
    public Laboratorio crear(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }
    
    public Laboratorio actualizar(Long id, Laboratorio lab) {
        Optional<Laboratorio> existe = laboratorioRepository.findById(id);
        if (existe.isPresent()) {
            Laboratorio l = existe.get();
            l.setNombre(lab.getNombre());
            l.setDireccion(lab.getDireccion());
            l.setEspecialidad(lab.getEspecialidad());
            l.setTelefono(lab.getTelefono());
            l.setEmail(lab.getEmail());
            l.setActivo(lab.getActivo());
            return laboratorioRepository.save(l);
        }
        return null;
    }
    
    public boolean eliminar(Long id) {
        if (laboratorioRepository.existsById(id)) {
            laboratorioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}