package com.centrocomunitario.service;

import com.centrocomunitario.model.Comentario;
import com.centrocomunitario.repository.ComentarioRepository;
import com.centrocomunitario.repository.ForoRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ForoRepository foroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear comentario
    public Comentario crearComentario(Comentario comentario) {
        if (!usuarioRepository.existsById(comentario.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + comentario.getIdUsuario());
        }
        if (!foroRepository.existsById(comentario.getIdForo())) {
            throw new RuntimeException("Foro no encontrado con ID: " + comentario.getIdForo());
        }
        if (comentario.getIdComentarioPadre() != null &&
                !comentarioRepository.existsById(comentario.getIdComentarioPadre())) {
            throw new RuntimeException("Comentario padre no encontrado con ID: "
                    + comentario.getIdComentarioPadre());
        }
        comentario.setFechaPublicacion(LocalDate.now());
        return comentarioRepository.save(comentario);
    }

    // Obtener todos los comentarios
    public List<Comentario> obtenerTodos() {
        return comentarioRepository.findAll();
    }

    // Buscar por ID
    public Comentario obtenerPorId(Integer id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + id));
    }

    // Obtener comentarios de un foro
    public List<Comentario> obtenerPorForo(Integer idForo) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        return comentarioRepository.findByIdForo(idForo);
    }

    // Obtener comentarios raiz de un foro (sin padre)
    public List<Comentario> obtenerRaicesPorForo(Integer idForo) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        return comentarioRepository.findByIdForoAndIdComentarioPadreIsNull(idForo);
    }

    // Obtener respuestas de un comentario
    public List<Comentario> obtenerRespuestas(Integer idComentarioPadre) {
        if (!comentarioRepository.existsById(idComentarioPadre)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + idComentarioPadre);
        }
        return comentarioRepository.findByIdComentarioPadre(idComentarioPadre);
    }

    // Obtener comentarios de un usuario
    public List<Comentario> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return comentarioRepository.findByIdUsuario(idUsuario);
    }

    // Obtener comentarios de un usuario en un foro
    public List<Comentario> obtenerPorForoYUsuario(Integer idForo, Integer idUsuario) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return comentarioRepository.findByIdForoAndIdUsuario(idForo, idUsuario);
    }

    // Obtener comentarios en un rango de fechas
    public List<Comentario> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return comentarioRepository.findByFechaPublicacionBetween(inicio, fin);
    }

    // Contar comentarios de un foro
    public long contarPorForo(Integer idForo) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        return comentarioRepository.countByIdForo(idForo);
    }

    // Contar respuestas de un comentario
    public long contarRespuestas(Integer idComentarioPadre) {
        if (!comentarioRepository.existsById(idComentarioPadre)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + idComentarioPadre);
        }
        return comentarioRepository.countByIdComentarioPadre(idComentarioPadre);
    }

    // Actualizar comentario
    public Comentario actualizarComentario(Integer id, Comentario datosNuevos) {
        Comentario existente = obtenerPorId(id);
        existente.setContenido(datosNuevos.getContenido());
        return comentarioRepository.save(existente);
    }

    // Eliminar comentario
    @Transactional
    public void eliminarComentario(Integer id) {
        if (!comentarioRepository.existsById(id)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }
}
