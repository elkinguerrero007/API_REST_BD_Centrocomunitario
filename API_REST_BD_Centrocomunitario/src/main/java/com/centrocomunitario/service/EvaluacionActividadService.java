package com.centrocomunitario.service;

import com.centrocomunitario.model.EvaluacionActividad;
import com.centrocomunitario.model.EvaluacionActividadId;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.EvaluacionActividadRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluacionActividadService {

    @Autowired
    private EvaluacionActividadRepository evaluacionActividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    // Crear evaluacion
    public EvaluacionActividad crearEvaluacion(EvaluacionActividad evaluacion) {
        if (!usuarioRepository.existsById(evaluacion.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + evaluacion.getIdUsuario());
        }
        if (!actividadRepository.existsById(evaluacion.getIdActividad())) {
            throw new RuntimeException("Actividad no encontrada con ID: " + evaluacion.getIdActividad());
        }
        if (evaluacionActividadRepository.existsByIdUsuarioAndIdActividad(
                evaluacion.getIdUsuario(), evaluacion.getIdActividad())) {
            throw new RuntimeException("El usuario ID: " + evaluacion.getIdUsuario()
                    + " ya evaluo la actividad ID: " + evaluacion.getIdActividad());
        }
        evaluacion.setFecha(LocalDateTime.now());
        return evaluacionActividadRepository.save(evaluacion);
    }

    // Obtener todas las evaluaciones
    public List<EvaluacionActividad> obtenerTodas() {
        return evaluacionActividadRepository.findAll();
    }

    // Obtener por clave compuesta
    public EvaluacionActividad obtenerPorId(Integer idUsuario, Integer idActividad) {
        EvaluacionActividadId id = new EvaluacionActividadId(idUsuario, idActividad);
        return evaluacionActividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluacion no encontrada para usuario ID: "
                        + idUsuario + " y actividad ID: " + idActividad));
    }

    // Obtener evaluaciones de una actividad
    public List<EvaluacionActividad> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return evaluacionActividadRepository.findByIdActividad(idActividad);
    }

    // Obtener evaluaciones de un usuario
    public List<EvaluacionActividad> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return evaluacionActividadRepository.findByIdUsuario(idUsuario);
    }

    // Obtener por valoracion
    public List<EvaluacionActividad> obtenerPorValoracion(Integer valoracion) {
        if (valoracion < 1 || valoracion > 5) {
            throw new RuntimeException("La valoracion debe estar entre 1 y 5");
        }
        return evaluacionActividadRepository.findByValoracion(valoracion);
    }

    // Obtener evaluaciones de una actividad por valoracion
    public List<EvaluacionActividad> obtenerPorActividadYValoracion(Integer idActividad, Integer valoracion) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        if (valoracion < 1 || valoracion > 5) {
            throw new RuntimeException("La valoracion debe estar entre 1 y 5");
        }
        return evaluacionActividadRepository.findByIdActividadAndValoracion(idActividad, valoracion);
    }

    // Obtener evaluaciones en un rango de fechas
    public List<EvaluacionActividad> obtenerPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return evaluacionActividadRepository.findByFechaBetween(inicio, fin);
    }

    // Obtener promedio de valoracion de una actividad
    public Double obtenerPromedioValoracion(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        Double promedio = evaluacionActividadRepository.promedioValoracionPorActividad(idActividad);
        return promedio != null ? promedio : 0.0;
    }

    // Actualizar evaluacion
    public EvaluacionActividad actualizarEvaluacion(Integer idUsuario, Integer idActividad,
                                                     EvaluacionActividad datosNuevos) {
        EvaluacionActividad existente = obtenerPorId(idUsuario, idActividad);
        existente.setValoracion(datosNuevos.getValoracion());
        existente.setObservaciones(datosNuevos.getObservaciones());
        existente.setSugerenciasMejora(datosNuevos.getSugerenciasMejora());
        existente.setFecha(LocalDateTime.now());
        return evaluacionActividadRepository.save(existente);
    }

    // Eliminar evaluacion
    public void eliminarEvaluacion(Integer idUsuario, Integer idActividad) {
        EvaluacionActividadId id = new EvaluacionActividadId(idUsuario, idActividad);
        if (!evaluacionActividadRepository.existsById(id)) {
            throw new RuntimeException("Evaluacion no encontrada para usuario ID: "
                    + idUsuario + " y actividad ID: " + idActividad);
        }
        evaluacionActividadRepository.deleteById(id);
    }

    // Eliminar todas las evaluaciones de una actividad
    @Transactional
    public void eliminarPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        evaluacionActividadRepository.deleteByIdActividad(idActividad);
    }

    // Eliminar todas las evaluaciones de un usuario
    @Transactional
    public void eliminarPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        evaluacionActividadRepository.deleteByIdUsuario(idUsuario);
    }
}
