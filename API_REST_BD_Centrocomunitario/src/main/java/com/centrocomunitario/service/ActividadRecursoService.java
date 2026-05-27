package com.centrocomunitario.service;

import com.centrocomunitario.model.ActividadRecurso;
import com.centrocomunitario.model.ActividadRecursoId;
import com.centrocomunitario.repository.ActividadRecursoRepository;
import com.centrocomunitario.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActividadRecursoService {

    @Autowired
    private ActividadRecursoRepository actividadRecursoRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    // Agregar recurso a una actividad
    public ActividadRecurso agregarRecurso(ActividadRecurso recurso) {
        if (!actividadRepository.existsById(recurso.getIdActividad())) {
            throw new RuntimeException("Actividad no encontrada con ID: " + recurso.getIdActividad());
        }
        if (actividadRecursoRepository.existsByIdActividadAndRecursoRequerido(
                recurso.getIdActividad(), recurso.getRecursoRequerido())) {
            throw new RuntimeException("El recurso '" + recurso.getRecursoRequerido()
                    + "' ya existe en la actividad con ID: " + recurso.getIdActividad());
        }
        return actividadRecursoRepository.save(recurso);
    }

    // Obtener todos los recursos
    public List<ActividadRecurso> obtenerTodos() {
        return actividadRecursoRepository.findAll();
    }

    // Obtener recursos de una actividad
    public List<ActividadRecurso> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return actividadRecursoRepository.findByIdActividad(idActividad);
    }

    // Buscar por palabra clave en el recurso
    public List<ActividadRecurso> buscarPorRecurso(String recurso) {
        return actividadRecursoRepository.findByRecursoRequeridoContainingIgnoreCase(recurso);
    }

    // Obtener recurso por clave compuesta
    public ActividadRecurso obtenerPorId(Integer idActividad, String recursoRequerido) {
        ActividadRecursoId id = new ActividadRecursoId(idActividad, recursoRequerido);
        return actividadRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso '" + recursoRequerido
                        + "' no encontrado en la actividad con ID: " + idActividad));
    }

    // Eliminar un recurso especifico de una actividad
    public void eliminarRecurso(Integer idActividad, String recursoRequerido) {
        ActividadRecursoId id = new ActividadRecursoId(idActividad, recursoRequerido);
        if (!actividadRecursoRepository.existsById(id)) {
            throw new RuntimeException("Recurso '" + recursoRequerido
                    + "' no encontrado en la actividad con ID: " + idActividad);
        }
        actividadRecursoRepository.deleteById(id);
    }

    // Eliminar todos los recursos de una actividad
    @Transactional
    public void eliminarRecursosPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        actividadRecursoRepository.deleteByIdActividad(idActividad);
    }
}
