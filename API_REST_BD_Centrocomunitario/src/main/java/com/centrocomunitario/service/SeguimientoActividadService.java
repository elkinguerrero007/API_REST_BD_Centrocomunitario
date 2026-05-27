package com.centrocomunitario.service;

import com.centrocomunitario.model.Actividad;
import com.centrocomunitario.model.SeguimientoActividad;
import com.centrocomunitario.model.SeguimientoActividadId;
import com.centrocomunitario.model.Usuario;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.InscripcionActividadRepository;
import com.centrocomunitario.repository.SeguimientoActividadRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeguimientoActividadService {

    @Autowired
    private SeguimientoActividadRepository seguimientoActividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private InscripcionActividadRepository inscripcionActividadRepository;

    // Registrar seguimiento
    public SeguimientoActividad registrarSeguimiento(SeguimientoActividad seguimiento) {
        // Validar usuario registrador
        if (!usuarioRepository.existsById(seguimiento.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + seguimiento.getIdUsuario());
        }

        // Validar que el instructor existe y tiene rol instructor
        Usuario instructor = usuarioRepository.findById(seguimiento.getIdInstructor())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con ID: " + seguimiento.getIdInstructor()));
        if (instructor.getRol() != Usuario.Rol.instructor) {
            throw new RuntimeException("El usuario ID: " + seguimiento.getIdInstructor() + " no tiene rol de instructor");
        }

        // Validar que el participante existe y tiene rol participante
        Usuario participante = usuarioRepository.findById(seguimiento.getIdParticipante())
                .orElseThrow(() -> new RuntimeException("Participante no encontrado con ID: " + seguimiento.getIdParticipante()));
        if (participante.getRol() != Usuario.Rol.participante) {
            throw new RuntimeException("El usuario ID: " + seguimiento.getIdParticipante() + " no tiene rol de participante");
        }

        // Validar que la actividad existe y no está finalizada/cancelada
        Actividad actividad = actividadRepository.findById(seguimiento.getIdActividad())
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + seguimiento.getIdActividad()));
        if (actividad.getEstado() == Actividad.Estado.finalizada ||
                actividad.getEstado() == Actividad.Estado.cancelada) {
            throw new RuntimeException("No se puede registrar seguimiento en una actividad con estado: " + actividad.getEstado());
        }

        // Validar que instructor y participante no sean el mismo usuario
        if (seguimiento.getIdInstructor().equals(seguimiento.getIdParticipante())) {
            throw new RuntimeException("El instructor y el participante no pueden ser el mismo usuario");
        }

        // Validar que el participante está matriculado en la actividad
        boolean estaMatriculado = inscripcionActividadRepository
                .findByIdActividadAndEstado(seguimiento.getIdActividad(), com.centrocomunitario.model.InscripcionActividad.Estado.matriculado)
                .stream()
                .anyMatch(i -> i.getIdUsuario().equals(seguimiento.getIdParticipante()));
        if (!estaMatriculado) {
            throw new RuntimeException("El participante ID: " + seguimiento.getIdParticipante()
                    + " no esta matriculado en la actividad ID: " + seguimiento.getIdActividad());
        }

        if (seguimiento.getFechaRegistro() == null) {
            seguimiento.setFechaRegistro(LocalDateTime.now());
        }
        return seguimientoActividadRepository.save(seguimiento);
    }

    // Actualizar seguimiento (solo campos no clave)
    public SeguimientoActividad actualizarSeguimiento(Integer idUsuario, Integer idInstructor,
                                                       Integer idParticipante, Integer idActividad,
                                                       LocalDateTime fechaRegistro,
                                                       SeguimientoActividad datosNuevos) {
        SeguimientoActividad existente = obtenerPorId(idUsuario, idInstructor, idParticipante,
                idActividad, fechaRegistro);
        existente.setComentario(datosNuevos.getComentario());
        existente.setAspectoEvaluado(datosNuevos.getAspectoEvaluado());
        existente.setNivelProgreso(datosNuevos.getNivelProgreso());
        existente.setObservaciones(datosNuevos.getObservaciones());
        return seguimientoActividadRepository.save(existente);
    }

    // Obtener todos los seguimientos
    public List<SeguimientoActividad> obtenerTodos() {
        return seguimientoActividadRepository.findAll();
    }

    // Obtener por clave compuesta (5 campos)
    public SeguimientoActividad obtenerPorId(Integer idUsuario, Integer idInstructor,
                                              Integer idParticipante, Integer idActividad,
                                              LocalDateTime fechaRegistro) {
        return seguimientoActividadRepository
                .findByClaveCompuesta(idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));
    }

    // Obtener seguimientos de un usuario (quien registra)
    public List<SeguimientoActividad> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return seguimientoActividadRepository.findByIdUsuario(idUsuario);
    }

    // Obtener seguimientos realizados por un instructor
    public List<SeguimientoActividad> obtenerPorInstructor(Integer idInstructor) {
        if (!usuarioRepository.existsById(idInstructor)) {
            throw new RuntimeException("Instructor no encontrado con ID: " + idInstructor);
        }
        return seguimientoActividadRepository.findByIdInstructor(idInstructor);
    }

    // Obtener seguimientos de un participante
    public List<SeguimientoActividad> obtenerPorParticipante(Integer idParticipante) {
        if (!usuarioRepository.existsById(idParticipante)) {
            throw new RuntimeException("Participante no encontrado con ID: " + idParticipante);
        }
        return seguimientoActividadRepository.findByIdParticipante(idParticipante);
    }

    // Obtener seguimientos de una actividad
    public List<SeguimientoActividad> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return seguimientoActividadRepository.findByIdActividad(idActividad);
    }

    // Obtener seguimientos de un instructor en una actividad
    public List<SeguimientoActividad> obtenerPorInstructorYActividad(Integer idInstructor, Integer idActividad) {
        if (!usuarioRepository.existsById(idInstructor)) {
            throw new RuntimeException("Instructor no encontrado con ID: " + idInstructor);
        }
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return seguimientoActividadRepository.findByIdInstructorAndIdActividad(idInstructor, idActividad);
    }

    // Obtener seguimientos de un participante en una actividad
    public List<SeguimientoActividad> obtenerPorParticipanteYActividad(Integer idParticipante, Integer idActividad) {
        if (!usuarioRepository.existsById(idParticipante)) {
            throw new RuntimeException("Participante no encontrado con ID: " + idParticipante);
        }
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return seguimientoActividadRepository.findByIdParticipanteAndIdActividad(idParticipante, idActividad);
    }

    // Obtener seguimientos entre instructor y participante
    public List<SeguimientoActividad> obtenerPorInstructorYParticipante(Integer idInstructor, Integer idParticipante) {
        if (!usuarioRepository.existsById(idInstructor)) {
            throw new RuntimeException("Instructor no encontrado con ID: " + idInstructor);
        }
        if (!usuarioRepository.existsById(idParticipante)) {
            throw new RuntimeException("Participante no encontrado con ID: " + idParticipante);
        }
        return seguimientoActividadRepository.findByIdInstructorAndIdParticipante(idInstructor, idParticipante);
    }

    // Obtener por rango de fechas
    public List<SeguimientoActividad> obtenerPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return seguimientoActividadRepository.findByFechaRegistroBetween(inicio, fin);
    }

    // Obtener por nivel de progreso
    public List<SeguimientoActividad> obtenerPorNivelProgreso(String nivelProgreso) {
        return seguimientoActividadRepository.findByNivelProgreso(nivelProgreso);
    }

    // Buscar por aspecto evaluado
    public List<SeguimientoActividad> buscarPorAspecto(String aspecto) {
        return seguimientoActividadRepository.findByAspectoEvaluadoContainingIgnoreCase(aspecto);
    }

    // Eliminar seguimiento por clave compuesta (5 campos)
    public void eliminarSeguimiento(Integer idUsuario, Integer idInstructor,
                                    Integer idParticipante, Integer idActividad,
                                    LocalDateTime fechaRegistro) {
        SeguimientoActividad existente = seguimientoActividadRepository
                .findByClaveCompuesta(idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));
        seguimientoActividadRepository.delete(existente);
    }
}
