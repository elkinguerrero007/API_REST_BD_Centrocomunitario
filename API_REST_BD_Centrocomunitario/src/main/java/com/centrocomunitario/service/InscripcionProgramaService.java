package com.centrocomunitario.service;

import com.centrocomunitario.model.InscripcionPrograma;
import com.centrocomunitario.model.InscripcionProgramaId;
import com.centrocomunitario.repository.InscripcionProgramaRepository;
import com.centrocomunitario.repository.ProgramaEspecialRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscripcionProgramaService {

    @Autowired
    private InscripcionProgramaRepository inscripcionProgramaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProgramaEspecialRepository programaEspecialRepository;

    // Inscribir usuario a programa
    public InscripcionPrograma inscribir(InscripcionPrograma inscripcion) {
        if (!usuarioRepository.existsById(inscripcion.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + inscripcion.getIdUsuario());
        }
        if (!programaEspecialRepository.existsById(inscripcion.getIdPrograma())) {
            throw new RuntimeException("Programa no encontrado con ID: " + inscripcion.getIdPrograma());
        }
        if (inscripcionProgramaRepository.existsByIdUsuarioAndIdPrograma(
                inscripcion.getIdUsuario(), inscripcion.getIdPrograma())) {
            throw new RuntimeException("El usuario ID: " + inscripcion.getIdUsuario()
                    + " ya esta inscrito en el programa ID: " + inscripcion.getIdPrograma());
        }
        if (inscripcion.getFechaInscripcion() == null) {
            inscripcion.setFechaInscripcion(LocalDate.now());
        }
        return inscripcionProgramaRepository.save(inscripcion);
    }

    // Obtener todas las inscripciones
    public List<InscripcionPrograma> obtenerTodas() {
        return inscripcionProgramaRepository.findAll();
    }

    // Obtener por clave compuesta
    public InscripcionPrograma obtenerPorId(Integer idUsuario, Integer idPrograma) {
        InscripcionProgramaId id = new InscripcionProgramaId(idUsuario, idPrograma);
        return inscripcionProgramaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada para usuario ID: "
                        + idUsuario + " y programa ID: " + idPrograma));
    }

    // Obtener inscripciones de un usuario
    public List<InscripcionPrograma> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return inscripcionProgramaRepository.findByIdUsuario(idUsuario);
    }

    // Obtener inscripciones de un programa
    public List<InscripcionPrograma> obtenerPorPrograma(Integer idPrograma) {
        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException("Programa no encontrado con ID: " + idPrograma);
        }
        return inscripcionProgramaRepository.findByIdPrograma(idPrograma);
    }

    // Obtener inscripciones en rango de fechas
    public List<InscripcionPrograma> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return inscripcionProgramaRepository.findByFechaInscripcionBetween(inicio, fin);
    }

    // Obtener inscripciones sin fecha registrada
    public List<InscripcionPrograma> obtenerSinFecha() {
        return inscripcionProgramaRepository.findByFechaInscripcionIsNull();
    }

    // Contar inscritos en un programa
    public long contarInscritos(Integer idPrograma) {
        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException("Programa no encontrado con ID: " + idPrograma);
        }
        return inscripcionProgramaRepository.countByIdPrograma(idPrograma);
    }

    // Actualizar fecha de inscripcion
    public InscripcionPrograma actualizarFecha(Integer idUsuario, Integer idPrograma, LocalDate nuevaFecha) {
        InscripcionPrograma inscripcion = obtenerPorId(idUsuario, idPrograma);
        inscripcion.setFechaInscripcion(nuevaFecha);
        return inscripcionProgramaRepository.save(inscripcion);
    }

    // Cancelar inscripcion
    public void cancelarInscripcion(Integer idUsuario, Integer idPrograma) {
        InscripcionProgramaId id = new InscripcionProgramaId(idUsuario, idPrograma);
        if (!inscripcionProgramaRepository.existsById(id)) {
            throw new RuntimeException("Inscripcion no encontrada para usuario ID: "
                    + idUsuario + " y programa ID: " + idPrograma);
        }
        inscripcionProgramaRepository.deleteById(id);
    }

    // Cancelar todas las inscripciones de un usuario
    @Transactional
    public void cancelarPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        inscripcionProgramaRepository.deleteByIdUsuario(idUsuario);
    }

    // Cancelar todas las inscripciones de un programa
    @Transactional
    public void cancelarPorPrograma(Integer idPrograma) {
        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException("Programa no encontrado con ID: " + idPrograma);
        }
        inscripcionProgramaRepository.deleteByIdPrograma(idPrograma);
    }
}
