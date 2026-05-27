package com.centrocomunitario.service;

import com.centrocomunitario.model.Actividad;
import com.centrocomunitario.model.InscripcionActividad;
import com.centrocomunitario.model.InscripcionActividadId;
import com.centrocomunitario.model.Sesion;
import com.centrocomunitario.model.Usuario;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.AsistenciaSesionRepository;
import com.centrocomunitario.repository.InscripcionActividadRepository;
import com.centrocomunitario.repository.SeguimientoActividadRepository;
import com.centrocomunitario.repository.SesionRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscripcionActividadService {

    @Autowired
    private InscripcionActividadRepository inscripcionActividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private AsistenciaSesionRepository asistenciaSesionRepository;

    @Autowired
    private SeguimientoActividadRepository seguimientoActividadRepository;

    // Inscribir usuario a actividad
    public InscripcionActividad inscribir(InscripcionActividad inscripcion) {
        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(inscripcion.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + inscripcion.getIdUsuario()));

        // Solo participantes pueden inscribirse
        if (usuario.getRol() != Usuario.Rol.participante) {
            throw new RuntimeException("Solo usuarios con rol 'participante' pueden inscribirse en actividades");
        }

        // Validar que la actividad existe
        Actividad actividad = actividadRepository.findById(inscripcion.getIdActividad())
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + inscripcion.getIdActividad()));

        // No se puede inscribir en actividad finalizada o cancelada
        if (actividad.getEstado() == Actividad.Estado.finalizada ||
                actividad.getEstado() == Actividad.Estado.cancelada) {
            throw new RuntimeException("No se puede inscribir en una actividad con estado: " + actividad.getEstado());
        }

        // Validar que no está ya inscrito
        if (inscripcionActividadRepository.existsByIdUsuarioAndIdActividad(
                inscripcion.getIdUsuario(), inscripcion.getIdActividad())) {
            throw new RuntimeException("El usuario ID: " + inscripcion.getIdUsuario()
                    + " ya esta inscrito en la actividad ID: " + inscripcion.getIdActividad());
        }

        // Validar cupo máximo
        if (actividad.getCupoMaximo() != null) {
            long inscritos = inscripcionActividadRepository.countByIdActividad(inscripcion.getIdActividad());
            if (inscritos >= actividad.getCupoMaximo()) {
                throw new RuntimeException("La actividad ID: " + inscripcion.getIdActividad()
                        + " ha alcanzado su cupo maximo de " + actividad.getCupoMaximo() + " participantes");
            }
        }

        // Forzar estado inscrito y fecha actual
        inscripcion.setEstado(InscripcionActividad.Estado.inscrito);
        inscripcion.setFechaInscripcion(LocalDate.now());
        return inscripcionActividadRepository.save(inscripcion);
    }

    // Obtener todas las inscripciones
    public List<InscripcionActividad> obtenerTodas() {
        return inscripcionActividadRepository.findAll();
    }

    // Obtener por clave compuesta
    public InscripcionActividad obtenerPorId(Integer idUsuario, Integer idActividad) {
        InscripcionActividadId id = new InscripcionActividadId(idUsuario, idActividad);
        return inscripcionActividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada para usuario ID: "
                        + idUsuario + " y actividad ID: " + idActividad));
    }

    // Obtener inscripciones de un usuario
    public List<InscripcionActividad> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return inscripcionActividadRepository.findByIdUsuario(idUsuario);
    }

    // Obtener inscripciones de una actividad
    public List<InscripcionActividad> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return inscripcionActividadRepository.findByIdActividad(idActividad);
    }

    // Obtener por estado
    public List<InscripcionActividad> obtenerPorEstado(InscripcionActividad.Estado estado) {
        return inscripcionActividadRepository.findByEstado(estado);
    }

    // Obtener inscripciones de una actividad por estado
    public List<InscripcionActividad> obtenerPorActividadYEstado(Integer idActividad,
                                                                   InscripcionActividad.Estado estado) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return inscripcionActividadRepository.findByIdActividadAndEstado(idActividad, estado);
    }

    // Obtener inscripciones de un usuario por estado
    public List<InscripcionActividad> obtenerPorUsuarioYEstado(Integer idUsuario,
                                                                InscripcionActividad.Estado estado) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return inscripcionActividadRepository.findByIdUsuarioAndEstado(idUsuario, estado);
    }

    // Obtener por rango de fechas
    public List<InscripcionActividad> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return inscripcionActividadRepository.findByFechaInscripcionBetween(inicio, fin);
    }

    // Contar inscritos en una actividad
    public long contarInscritos(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return inscripcionActividadRepository.countByIdActividad(idActividad);
    }

    // Contar inscritos por estado en una actividad
    public long contarPorEstado(Integer idActividad, InscripcionActividad.Estado estado) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return inscripcionActividadRepository.countByIdActividadAndEstado(idActividad, estado);
    }

    // Cambiar estado de inscripcion
    public InscripcionActividad cambiarEstado(Integer idUsuario, Integer idActividad,
                                               InscripcionActividad.Estado nuevoEstado) {
        InscripcionActividad inscripcion = obtenerPorId(idUsuario, idActividad);
        inscripcion.setEstado(nuevoEstado);
        return inscripcionActividadRepository.save(inscripcion);
    }

    // Cancelar inscripcion (con borrado lógico si ya tiene historial)
    /**
     * Cancela la inscripción de un participante.
     * Si el participante ya tiene asistencias a sesiones o seguimientos registrados
     * en esa actividad, aplica BORRADO LÓGICO cambiando el estado a "retirado".
     * Si no tiene historial, elimina físicamente el registro.
     *
     * @return true si se aplicó borrado lógico, false si se eliminó físicamente
     */
    public boolean cancelarInscripcion(Integer idUsuario, Integer idActividad) {
        InscripcionActividadId id = new InscripcionActividadId(idUsuario, idActividad);
        InscripcionActividad inscripcion = inscripcionActividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada para usuario ID: "
                        + idUsuario + " y actividad ID: " + idActividad));

        // Verificar si tiene asistencias en alguna sesión de esta actividad
        List<Sesion> sesiones = sesionRepository.findByIdActividad(idActividad);
        boolean tieneAsistencias = sesiones.stream()
                .anyMatch(s -> asistenciaSesionRepository
                        .existsByIdUsuarioAndIdSesion(idUsuario, s.getIdSesion()));

        // Verificar si tiene seguimientos en esta actividad
        boolean tieneSeguimientos = !seguimientoActividadRepository
                .findByIdParticipanteAndIdActividad(idUsuario, idActividad).isEmpty();

        if (tieneAsistencias || tieneSeguimientos) {
            // Borrado lógico: cambiar estado a retirado
            inscripcion.setEstado(InscripcionActividad.Estado.retirado);
            inscripcionActividadRepository.save(inscripcion);
            return true; // borrado lógico aplicado
        } else {
            inscripcionActividadRepository.deleteById(id);
            return false; // eliminado físicamente
        }
    }

    // Cancelar todas las inscripciones de un usuario
    @Transactional
    public void cancelarPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        inscripcionActividadRepository.deleteByIdUsuario(idUsuario);
    }

    // Cancelar todas las inscripciones de una actividad
    @Transactional
    public void cancelarPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        inscripcionActividadRepository.deleteByIdActividad(idActividad);
    }
}
