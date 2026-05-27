package com.centrocomunitario.service;

import com.centrocomunitario.model.AsistenciaSesion;
import com.centrocomunitario.model.AsistenciaSesionId;
import com.centrocomunitario.repository.AsistenciaSesionRepository;
import com.centrocomunitario.repository.SesionRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsistenciaSesionService {

    @Autowired
    private AsistenciaSesionRepository asistenciaSesionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SesionRepository sesionRepository;

    // Registrar asistencia
    public AsistenciaSesion registrarAsistencia(AsistenciaSesion asistencia) {
        if (!usuarioRepository.existsById(asistencia.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + asistencia.getIdUsuario());
        }
        if (!sesionRepository.existsById(asistencia.getIdSesion())) {
            throw new RuntimeException("Sesion no encontrada con ID: " + asistencia.getIdSesion());
        }
        if (asistenciaSesionRepository.existsByIdUsuarioAndIdSesion(
                asistencia.getIdUsuario(), asistencia.getIdSesion())) {
            throw new RuntimeException("Ya existe un registro de asistencia para el usuario ID: "
                    + asistencia.getIdUsuario() + " en la sesion ID: " + asistencia.getIdSesion());
        }
        return asistenciaSesionRepository.save(asistencia);
    }

    // Obtener todas las asistencias
    public List<AsistenciaSesion> obtenerTodas() {
        return asistenciaSesionRepository.findAll();
    }

    // Obtener por clave compuesta
    public AsistenciaSesion obtenerPorId(Integer idUsuario, Integer idSesion) {
        AsistenciaSesionId id = new AsistenciaSesionId(idUsuario, idSesion);
        return asistenciaSesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de asistencia no encontrado para usuario ID: "
                        + idUsuario + " en sesion ID: " + idSesion));
    }

    // Obtener asistencias de una sesion
    public List<AsistenciaSesion> obtenerPorSesion(Integer idSesion) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        return asistenciaSesionRepository.findByIdSesion(idSesion);
    }

    // Obtener asistencias de un usuario
    public List<AsistenciaSesion> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return asistenciaSesionRepository.findByIdUsuario(idUsuario);
    }

    // Obtener por estado
    public List<AsistenciaSesion> obtenerPorEstado(String estado) {
        try {
            AsistenciaSesion.Estado.fromValor(estado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado no valido. Use: asistio, no asistio");
        }
        return asistenciaSesionRepository.findByEstado(estado);
    }

    // Obtener asistencias de una sesion filtradas por estado
    public List<AsistenciaSesion> obtenerPorSesionYEstado(Integer idSesion, String estado) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        try {
            AsistenciaSesion.Estado.fromValor(estado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado no valido. Use: asistio, no asistio");
        }
        return asistenciaSesionRepository.findByIdSesionAndEstado(idSesion, estado);
    }

    // Contar asistentes de una sesion
    public long contarAsistentes(Integer idSesion) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        return asistenciaSesionRepository.countByIdSesionAndEstado(
                idSesion, AsistenciaSesion.Estado.asistio);
    }

    // Actualizar asistencia
    public AsistenciaSesion actualizarAsistencia(Integer idUsuario, Integer idSesion,
                                                  AsistenciaSesion datosNuevos) {
        AsistenciaSesion existente = obtenerPorId(idUsuario, idSesion);
        existente.setHoraRegistro(datosNuevos.getHoraRegistro());
        existente.setEstado(datosNuevos.getEstado());
        existente.setObservaciones(datosNuevos.getObservaciones());
        return asistenciaSesionRepository.save(existente);
    }

    // Eliminar asistencia
    public void eliminarAsistencia(Integer idUsuario, Integer idSesion) {
        AsistenciaSesionId id = new AsistenciaSesionId(idUsuario, idSesion);
        if (!asistenciaSesionRepository.existsById(id)) {
            throw new RuntimeException("Registro de asistencia no encontrado para usuario ID: "
                    + idUsuario + " en sesion ID: " + idSesion);
        }
        asistenciaSesionRepository.deleteById(id);
    }

    // Eliminar todas las asistencias de una sesion
    @Transactional
    public void eliminarPorSesion(Integer idSesion) {
        if (!sesionRepository.existsById(idSesion)) {
            throw new RuntimeException("Sesion no encontrada con ID: " + idSesion);
        }
        asistenciaSesionRepository.deleteByIdSesion(idSesion);
    }

    // Eliminar todas las asistencias de un usuario
    @Transactional
    public void eliminarPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        asistenciaSesionRepository.deleteByIdUsuario(idUsuario);
    }
}
