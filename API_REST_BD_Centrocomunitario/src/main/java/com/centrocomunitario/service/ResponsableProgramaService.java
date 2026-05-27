package com.centrocomunitario.service;

import com.centrocomunitario.model.ResponsablePrograma;
import com.centrocomunitario.model.ResponsableProgramaId;
import com.centrocomunitario.repository.ProgramaEspecialRepository;
import com.centrocomunitario.repository.ResponsableProgramaRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResponsableProgramaService {

    @Autowired
    private ResponsableProgramaRepository responsableProgramaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProgramaEspecialRepository programaEspecialRepository;

    // Asignar responsable a programa
    public ResponsablePrograma asignarResponsable(ResponsablePrograma responsable) {
        if (!usuarioRepository.existsById(responsable.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + responsable.getIdUsuario());
        }
        if (!programaEspecialRepository.existsById(responsable.getIdPrograma())) {
            throw new RuntimeException("Programa no encontrado con ID: " + responsable.getIdPrograma());
        }
        if (responsableProgramaRepository.existsByIdUsuarioAndIdPrograma(
                responsable.getIdUsuario(), responsable.getIdPrograma())) {
            throw new RuntimeException("El usuario ID: " + responsable.getIdUsuario()
                    + " ya es responsable del programa ID: " + responsable.getIdPrograma());
        }
        return responsableProgramaRepository.save(responsable);
    }

    // Obtener todos los responsables
    public List<ResponsablePrograma> obtenerTodos() {
        return responsableProgramaRepository.findAll();
    }

    // Obtener por clave compuesta
    public ResponsablePrograma obtenerPorId(Integer idUsuario, Integer idPrograma) {
        ResponsableProgramaId id = new ResponsableProgramaId(idUsuario, idPrograma);
        return responsableProgramaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario ID: " + idUsuario
                        + " no es responsable del programa ID: " + idPrograma));
    }

    // Obtener programas de los que es responsable un usuario
    public List<ResponsablePrograma> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return responsableProgramaRepository.findByIdUsuario(idUsuario);
    }

    // Obtener responsables de un programa
    public List<ResponsablePrograma> obtenerPorPrograma(Integer idPrograma) {
        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException("Programa no encontrado con ID: " + idPrograma);
        }
        return responsableProgramaRepository.findByIdPrograma(idPrograma);
    }

    // Contar responsables de un programa
    public long contarResponsables(Integer idPrograma) {
        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException("Programa no encontrado con ID: " + idPrograma);
        }
        return responsableProgramaRepository.countByIdPrograma(idPrograma);
    }

    // Eliminar responsable de un programa
    public void eliminarResponsable(Integer idUsuario, Integer idPrograma) {
        ResponsableProgramaId id = new ResponsableProgramaId(idUsuario, idPrograma);
        if (!responsableProgramaRepository.existsById(id)) {
            throw new RuntimeException("El usuario ID: " + idUsuario
                    + " no es responsable del programa ID: " + idPrograma);
        }
        responsableProgramaRepository.deleteById(id);
    }

    // Eliminar todos los programas a cargo de un usuario
    @Transactional
    public void eliminarPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        responsableProgramaRepository.deleteByIdUsuario(idUsuario);
    }

    // Eliminar todos los responsables de un programa
    @Transactional
    public void eliminarPorPrograma(Integer idPrograma) {
        if (!programaEspecialRepository.existsById(idPrograma)) {
            throw new RuntimeException("Programa no encontrado con ID: " + idPrograma);
        }
        responsableProgramaRepository.deleteByIdPrograma(idPrograma);
    }
}
