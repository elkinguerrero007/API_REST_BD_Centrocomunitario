package com.centrocomunitario.service;

import com.centrocomunitario.model.SesionArchivo;
import com.centrocomunitario.model.SesionArchivoId;
import com.centrocomunitario.repository.SesionArchivoRepository;
import com.centrocomunitario.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SesionArchivoService {

    @Autowired
    private SesionArchivoRepository sesionArchivoRepository;

    @Autowired
    private SesionRepository sesionRepository;

    // Agregar archivo a una sesion
    public SesionArchivo agregarArchivo(SesionArchivo archivo) {
        if (!sesionRepository.existsById(archivo.getIdSesion())) {
            throw new RuntimeException("Sesion no encontrada con ID: " + archivo.getIdSesion());
        }
        if (sesionArchivoRepository.existsByArchivoAdjuntoAndIdSesion(
                archivo.getArchivoAdjunto(), archivo.getIdSesion())) {
            throw new RuntimeException("Ya existe el archivo '" + archivo.getArchivoAdjunto()
                    + "' en la sesion con ID: " + archivo.getIdSesion());
        }
        return sesionArchivoRepository.save(archivo);
    }

    // Obtener todos los archivos
    public List<SesionArchivo> obtenerTodos() {
        return sesionArchivoRepository.findAll();
    }

    // Obtener archivos de una sesion
    public List<SesionArchivo> obtenerPorSesion(Integer idSesion) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        return sesionArchivoRepository.findByIdSesion(idSesion);
    }

    // Obtener por clave compuesta
    public SesionArchivo obtenerPorId(String archivoAdjunto, Integer idSesion) {
        SesionArchivoId id = new SesionArchivoId(archivoAdjunto, idSesion);
        return sesionArchivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archivo '" + archivoAdjunto
                        + "' no encontrado en la sesion con ID: " + idSesion));
    }

    // Obtener por tipo
    public List<SesionArchivo> obtenerPorTipo(SesionArchivo.Tipo tipo) {
        return sesionArchivoRepository.findByTipo(tipo);
    }

    // Obtener archivos de una sesion filtrados por tipo
    public List<SesionArchivo> obtenerPorSesionYTipo(Integer idSesion, SesionArchivo.Tipo tipo) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        return sesionArchivoRepository.findByIdSesionAndTipo(idSesion, tipo);
    }

    // Buscar por nombre
    public List<SesionArchivo> buscarPorNombre(String nombre) {
        return sesionArchivoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Eliminar archivo especifico
    public void eliminarArchivo(String archivoAdjunto, Integer idSesion) {
        SesionArchivoId id = new SesionArchivoId(archivoAdjunto, idSesion);
        if (!sesionArchivoRepository.existsById(id)) {
            throw new RuntimeException("Archivo '" + archivoAdjunto
                    + "' no encontrado en la sesion con ID: " + idSesion);
        }
        sesionArchivoRepository.deleteById(id);
    }

    // Eliminar todos los archivos de una sesion
    @Transactional
    public void eliminarArchivosPorSesion(Integer idSesion) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        sesionArchivoRepository.deleteByIdSesion(idSesion);
    }
}
