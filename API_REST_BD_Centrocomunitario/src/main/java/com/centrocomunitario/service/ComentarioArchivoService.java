package com.centrocomunitario.service;

import com.centrocomunitario.model.ComentarioArchivo;
import com.centrocomunitario.model.ComentarioArchivoId;
import com.centrocomunitario.repository.ComentarioArchivoRepository;
import com.centrocomunitario.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComentarioArchivoService {

    @Autowired
    private ComentarioArchivoRepository comentarioArchivoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Agregar archivo a un comentario
    public ComentarioArchivo agregarArchivo(ComentarioArchivo archivo) {
        if (!comentarioRepository.existsById(archivo.getIdComentario())) {
            throw new RuntimeException("Comentario no encontrado con ID: " + archivo.getIdComentario());
        }
        if (comentarioArchivoRepository.existsByIdComentarioAndArchivoAdjunto(
                archivo.getIdComentario(), archivo.getArchivoAdjunto())) {
            throw new RuntimeException("Ya existe el archivo '" + archivo.getArchivoAdjunto()
                    + "' en el comentario con ID: " + archivo.getIdComentario());
        }
        return comentarioArchivoRepository.save(archivo);
    }

    // Obtener todos los archivos
    public List<ComentarioArchivo> obtenerTodos() {
        return comentarioArchivoRepository.findAll();
    }

    // Obtener archivos de un comentario
    public List<ComentarioArchivo> obtenerPorComentario(Integer idComentario) {
        if (!comentarioRepository.existsById(idComentario)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + idComentario);
        }
        return comentarioArchivoRepository.findByIdComentario(idComentario);
    }

    // Obtener por clave compuesta
    public ComentarioArchivo obtenerPorId(Integer idComentario, String archivoAdjunto) {
        ComentarioArchivoId id = new ComentarioArchivoId(idComentario, archivoAdjunto);
        return comentarioArchivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archivo '" + archivoAdjunto
                        + "' no encontrado en el comentario con ID: " + idComentario));
    }

    // Obtener por tipo
    public List<ComentarioArchivo> obtenerPorTipo(ComentarioArchivo.Tipo tipo) {
        return comentarioArchivoRepository.findByTipo(tipo);
    }

    // Obtener archivos de un comentario filtrados por tipo
    public List<ComentarioArchivo> obtenerPorComentarioYTipo(Integer idComentario, ComentarioArchivo.Tipo tipo) {
        if (!comentarioRepository.existsById(idComentario)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + idComentario);
        }
        return comentarioArchivoRepository.findByIdComentarioAndTipo(idComentario, tipo);
    }

    // Buscar por nombre
    public List<ComentarioArchivo> buscarPorNombre(String nombre) {
        return comentarioArchivoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Eliminar archivo especifico
    public void eliminarArchivo(Integer idComentario, String archivoAdjunto) {
        ComentarioArchivoId id = new ComentarioArchivoId(idComentario, archivoAdjunto);
        if (!comentarioArchivoRepository.existsById(id)) {
            throw new RuntimeException("Archivo '" + archivoAdjunto
                    + "' no encontrado en el comentario con ID: " + idComentario);
        }
        comentarioArchivoRepository.deleteById(id);
    }

    // Eliminar todos los archivos de un comentario
    @Transactional
    public void eliminarArchivosPorComentario(Integer idComentario) {
        if (!comentarioRepository.existsById(idComentario)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + idComentario);
        }
        comentarioArchivoRepository.deleteByIdComentario(idComentario);
    }
}
