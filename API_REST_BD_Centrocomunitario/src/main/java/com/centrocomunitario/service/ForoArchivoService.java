package com.centrocomunitario.service;

import com.centrocomunitario.model.ForoArchivo;
import com.centrocomunitario.model.ForoArchivoId;
import com.centrocomunitario.repository.ForoArchivoRepository;
import com.centrocomunitario.repository.ForoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForoArchivoService {

    @Autowired
    private ForoArchivoRepository foroArchivoRepository;

    @Autowired
    private ForoRepository foroRepository;

    // Agregar archivo a un foro
    public ForoArchivo agregarArchivo(ForoArchivo archivo) {
        if (!foroRepository.existsById(archivo.getIdForo())) {
            throw new RuntimeException("Foro no encontrado con ID: " + archivo.getIdForo());
        }
        if (foroArchivoRepository.existsByArchivoAdjuntoAndIdForo(
                archivo.getArchivoAdjunto(), archivo.getIdForo())) {
            throw new RuntimeException("Ya existe el archivo '" + archivo.getArchivoAdjunto()
                    + "' en el foro con ID: " + archivo.getIdForo());
        }
        return foroArchivoRepository.save(archivo);
    }

    // Obtener todos los archivos
    public List<ForoArchivo> obtenerTodos() {
        return foroArchivoRepository.findAll();
    }

    // Obtener archivos de un foro
    public List<ForoArchivo> obtenerPorForo(Integer idForo) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        return foroArchivoRepository.findByIdForo(idForo);
    }

    // Obtener por clave compuesta
    public ForoArchivo obtenerPorId(String archivoAdjunto, Integer idForo) {
        ForoArchivoId id = new ForoArchivoId(archivoAdjunto, idForo);
        return foroArchivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archivo '" + archivoAdjunto
                        + "' no encontrado en el foro con ID: " + idForo));
    }

    // Obtener por tipo
    public List<ForoArchivo> obtenerPorTipo(ForoArchivo.Tipo tipo) {
        return foroArchivoRepository.findByTipo(tipo);
    }

    // Obtener archivos de un foro filtrados por tipo
    public List<ForoArchivo> obtenerPorForoYTipo(Integer idForo, ForoArchivo.Tipo tipo) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        return foroArchivoRepository.findByIdForoAndTipo(idForo, tipo);
    }

    // Buscar por nombre
    public List<ForoArchivo> buscarPorNombre(String nombre) {
        return foroArchivoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Eliminar archivo especifico
    public void eliminarArchivo(String archivoAdjunto, Integer idForo) {
        ForoArchivoId id = new ForoArchivoId(archivoAdjunto, idForo);
        if (!foroArchivoRepository.existsById(id)) {
            throw new RuntimeException("Archivo '" + archivoAdjunto
                    + "' no encontrado en el foro con ID: " + idForo);
        }
        foroArchivoRepository.deleteById(id);
    }

    // Eliminar todos los archivos de un foro
    @Transactional
    public void eliminarArchivosPorForo(Integer idForo) {
        if (!foroRepository.existsById(idForo)) {
            throw new RuntimeException("Foro no encontrado con ID: " + idForo);
        }
        foroArchivoRepository.deleteByIdForo(idForo);
    }
}
