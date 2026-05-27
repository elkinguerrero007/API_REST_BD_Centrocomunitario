package com.centrocomunitario.service;

import com.centrocomunitario.model.Actividad;
import com.centrocomunitario.model.Usuario;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.CategoriaRepository;
import com.centrocomunitario.repository.InscripcionActividadRepository;
import com.centrocomunitario.repository.ProgramaEspecialRepository;
import com.centrocomunitario.repository.SesionRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProgramaEspecialRepository programaEspecialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InscripcionActividadRepository inscripcionActividadRepository;

    @Autowired
    private SesionRepository sesionRepository;

    // ── CREAR ────────────────────────────────────────────────────────

    public Actividad crearActividad(Actividad actividad) {

        validarRelaciones(actividad);

        // Validar fechas
        if (actividad.getFechaFinalizacion().isBefore(actividad.getFechaInicio())) {
            throw new RuntimeException(
                    "La fecha de finalizacion no puede ser anterior a la fecha de inicio"
            );
        }

        // Validar rol del proponente:
        // solo personal_centro o lider_comunitario pueden proponer actividades
        Usuario proponente = usuarioRepository
                .findById(actividad.getIdProponente())
                .orElseThrow(() -> new RuntimeException(
                        "Proponente no encontrado con ID: "
                                + actividad.getIdProponente()
                ));

        if (proponente.getRol() != Usuario.Rol.personal_centro &&
            proponente.getRol() != Usuario.Rol.lider_comunitario) {

            throw new RuntimeException(
                    "Solo usuarios con rol personal_centro o lider_comunitario pueden proponer actividades"
            );
        }

        return actividadRepository.save(actividad);
    }

    // ── CONSULTAR ────────────────────────────────────────────────────

    public List<Actividad> obtenerTodas() {
        return actividadRepository.findAll();
    }

    public Actividad obtenerPorId(Integer id) {
        return actividadRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Actividad no encontrada con ID: " + id)
                );
    }

    public List<Actividad> buscarPorNombre(String nombre) {
        return actividadRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Actividad> obtenerPorEstado(Actividad.Estado estado) {
        return actividadRepository.findByEstado(estado);
    }

    public List<Actividad> obtenerPorCategoria(Integer idCategoria) {

        if (!categoriaRepository.existsById(idCategoria)) {
            throw new RuntimeException(
                    "Categoria no encontrada con ID: " + idCategoria
            );
        }

        return actividadRepository.findByIdCategoria(idCategoria);
    }

    public List<Actividad> obtenerPorPrograma(Integer idPrograma) {

        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException(
                    "Programa no encontrado con ID: " + idPrograma
            );
        }

        return actividadRepository.findByIdPrograma(idPrograma);
    }

    public List<Actividad> obtenerPorInstructor(Integer idInstructor) {

        if (!usuarioRepository.existsById(idInstructor)) {
            throw new RuntimeException(
                    "Instructor no encontrado con ID: " + idInstructor
            );
        }

        return actividadRepository.findByIdInstructor(idInstructor);
    }

    public List<Actividad> obtenerPorProponente(Integer idProponente) {

        if (!usuarioRepository.existsById(idProponente)) {
            throw new RuntimeException(
                    "Proponente no encontrado con ID: " + idProponente
            );
        }

        return actividadRepository.findByIdProponente(idProponente);
    }

    public List<Actividad> obtenerPorAprobador(Integer idAprobador) {

        if (!usuarioRepository.existsById(idAprobador)) {
            throw new RuntimeException(
                    "Aprobador no encontrado con ID: " + idAprobador
            );
        }

        return actividadRepository.findByIdAprobador(idAprobador);
    }

    public List<Actividad> obtenerPorRangoFechas(
            LocalDate inicio,
            LocalDate fin
    ) {

        if (fin.isBefore(inicio)) {
            throw new RuntimeException(
                    "La fecha fin no puede ser anterior a la fecha inicio"
            );
        }

        return actividadRepository.findByFechaInicioBetween(inicio, fin);
    }

    public List<Actividad> obtenerVigentes() {
        return actividadRepository.findByFechaFinalizacionGreaterThanEqual(
                LocalDate.now()
        );
    }

    // ── ACTUALIZAR ───────────────────────────────────────────────────

    public Actividad actualizarActividad(
            Integer id,
            Actividad datosNuevos
    ) {

        Actividad actividadExistente = obtenerPorId(id);

        validarRelaciones(datosNuevos);

        if (datosNuevos.getFechaFinalizacion()
                .isBefore(datosNuevos.getFechaInicio())) {

            throw new RuntimeException(
                    "La fecha de finalizacion no puede ser anterior a la fecha de inicio"
            );
        }

        actividadExistente.setNombre(datosNuevos.getNombre());
        actividadExistente.setDescripcion(datosNuevos.getDescripcion());
        actividadExistente.setObjetivo(datosNuevos.getObjetivo());
        actividadExistente.setFechaInicio(datosNuevos.getFechaInicio());
        actividadExistente.setFechaFinalizacion(
                datosNuevos.getFechaFinalizacion()
        );
        actividadExistente.setIntensidadHoraria(
                datosNuevos.getIntensidadHoraria()
        );
        actividadExistente.setCupoMaximo(datosNuevos.getCupoMaximo());
        actividadExistente.setEstado(datosNuevos.getEstado());
        actividadExistente.setIdCategoria(datosNuevos.getIdCategoria());
        actividadExistente.setIdPrograma(datosNuevos.getIdPrograma());
        actividadExistente.setIdInstructor(datosNuevos.getIdInstructor());
        actividadExistente.setIdProponente(datosNuevos.getIdProponente());
        actividadExistente.setIdAprobador(datosNuevos.getIdAprobador());

        return actividadRepository.save(actividadExistente);
    }

    public Actividad cambiarEstado(
            Integer id,
            Actividad.Estado nuevoEstado
    ) {

        Actividad actividad = obtenerPorId(id);

        actividad.setEstado(nuevoEstado);

        return actividadRepository.save(actividad);
    }

    // ── ELIMINAR ─────────────────────────────────────────────────────

    /**
     * Elimina una actividad.
     * Si tiene participantes inscritos o sesiones asignadas,
     * aplica borrado lógico cambiando el estado a "cancelada".
     *
     * @return true si se aplicó borrado lógico,
     * false si se eliminó físicamente
     */
    public boolean eliminarActividad(Integer id) {

        Actividad actividad = obtenerPorId(id);

        boolean tieneInscritos =
                inscripcionActividadRepository.countByIdActividad(id) > 0;

        boolean tieneSesiones =
                !sesionRepository.findByIdActividad(id).isEmpty();

        if (tieneInscritos || tieneSesiones) {

            // Borrado lógico
            actividad.setEstado(Actividad.Estado.cancelada);

            actividadRepository.save(actividad);

            return true;

        } else {

            actividadRepository.deleteById(id);

            return false;
        }
    }

    // ── MÉTODOS PRIVADOS ─────────────────────────────────────────────

    private void validarRelaciones(Actividad actividad) {

        if (!categoriaRepository.existsById(actividad.getIdCategoria())) {

            throw new RuntimeException(
                    "Categoria no encontrada con ID: "
                            + actividad.getIdCategoria()
            );
        }

        if (actividad.getIdPrograma() != null &&
            !programaEspecialRepository.existsById(
                    actividad.getIdPrograma()
            )) {

            throw new RuntimeException(
                    "Programa no encontrado con ID: "
                            + actividad.getIdPrograma()
            );
        }

        if (actividad.getIdInstructor() != null &&
            !usuarioRepository.existsById(
                    actividad.getIdInstructor()
            )) {

            throw new RuntimeException(
                    "Instructor no encontrado con ID: "
                            + actividad.getIdInstructor()
            );
        }

        if (!usuarioRepository.existsById(
                actividad.getIdProponente()
        )) {

            throw new RuntimeException(
                    "Proponente no encontrado con ID: "
                            + actividad.getIdProponente()
            );
        }

        if (!usuarioRepository.existsById(
                actividad.getIdAprobador()
        )) {

            throw new RuntimeException(
                    "Aprobador no encontrado con ID: "
                            + actividad.getIdAprobador()
            );
        }
    }
}