package com.centrocomunitario.service;

import com.centrocomunitario.model.ProgramaEspecial;
import com.centrocomunitario.repository.ProgramaEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgramaEspecialService {

    @Autowired
    private ProgramaEspecialRepository programaEspecialRepository;

    // Crear programa
    public ProgramaEspecial crearPrograma(ProgramaEspecial programa) {
        if (programaEspecialRepository.existsByNombre(programa.getNombre())) {
            throw new RuntimeException("Ya existe un programa con el nombre: " + programa.getNombre());
        }
        if (programa.getFechaFin().isBefore(programa.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        return programaEspecialRepository.save(programa);
    }

    // Obtener todos los programas
    public List<ProgramaEspecial> obtenerTodos() {
        return programaEspecialRepository.findAll();
    }

    // Buscar por ID
    public ProgramaEspecial obtenerPorId(Integer id) {
        return programaEspecialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado con ID: " + id));
    }

    // Buscar por nombre exacto
    public ProgramaEspecial obtenerPorNombre(String nombre) {
        return programaEspecialRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado con nombre: " + nombre));
    }

    // Buscar por palabra clave en el nombre
    public List<ProgramaEspecial> buscarPorNombre(String nombre) {
        return programaEspecialRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar por tipo
    public List<ProgramaEspecial> obtenerPorTipo(String tipo) {
        return programaEspecialRepository.findByTipo(tipo);
    }

    // Buscar por poblacion objetivo
    public List<ProgramaEspecial> obtenerPorPoblacion(String poblacion) {
        return programaEspecialRepository.findByPoblacionObjetivo(poblacion);
    }

    // Buscar programas en un rango de fechas de inicio
    public List<ProgramaEspecial> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return programaEspecialRepository.findByFechaInicioBetween(inicio, fin);
    }

    // Buscar programas activos (fecha fin >= hoy)
    public List<ProgramaEspecial> obtenerProgramasActivos() {
        return programaEspecialRepository.findByFechaFinGreaterThanEqual(LocalDate.now());
    }

    // Actualizar programa
    public ProgramaEspecial actualizarPrograma(Integer id, ProgramaEspecial datosNuevos) {
        ProgramaEspecial programaExistente = obtenerPorId(id);

        if (!programaExistente.getNombre().equals(datosNuevos.getNombre()) &&
                programaEspecialRepository.existsByNombre(datosNuevos.getNombre())) {
            throw new RuntimeException("Ya existe un programa con el nombre: " + datosNuevos.getNombre());
        }
        if (datosNuevos.getFechaFin().isBefore(datosNuevos.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        programaExistente.setNombre(datosNuevos.getNombre());
        programaExistente.setDescripcion(datosNuevos.getDescripcion());
        programaExistente.setFechaInicio(datosNuevos.getFechaInicio());
        programaExistente.setFechaFin(datosNuevos.getFechaFin());
        programaExistente.setPoblacionObjetivo(datosNuevos.getPoblacionObjetivo());
        programaExistente.setTipo(datosNuevos.getTipo());

        return programaEspecialRepository.save(programaExistente);
    }

    // Eliminar programa
    public void eliminarPrograma(Integer id) {
        if (!programaEspecialRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Programa no encontrado con ID: " + id);
        }
        programaEspecialRepository.deleteById(id);
    }
}
