package com.centrocomunitario.service;

import com.centrocomunitario.model.Foro;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.ForoRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForoService {

    @Autowired
    private ForoRepository foroRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear foro
    public Foro crearForo(Foro foro) {
        if (!actividadRepository.existsById(foro.getIdActividad())) {
            throw new RuntimeException("Actividad no encontrada con ID: " + foro.getIdActividad());
        }
        if (!usuarioRepository.existsById(foro.getIdUsuarioCreador())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + foro.getIdUsuarioCreador());
        }
        if (foroRepository.existsByTituloAndIdActividad(foro.getTitulo(), foro.getIdActividad())) {
            throw new RuntimeException("Ya existe un foro con el titulo '" + foro.getTitulo()
                    + "' en la actividad ID: " + foro.getIdActividad());
        }
        foro.setFechaPublicacion(LocalDateTime.now());
        return foroRepository.save(foro);
    }

    // Obtener todos los foros
    public List<Foro> obtenerTodos() {
        return foroRepository.findAll();
    }

    // Buscar por ID
    public Foro obtenerPorId(Integer id) {
        return foroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foro no encontrado con ID: " + id));
    }

    // Obtener foros de una actividad
    public List<Foro> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return foroRepository.findByIdActividad(idActividad);
    }

    // Obtener foros de un usuario
    public List<Foro> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return foroRepository.findByIdUsuarioCreador(idUsuario);
    }

    // Obtener por estado
    public List<Foro> obtenerPorEstado(Foro.Estado estado) {
        return foroRepository.findByEstado(estado);
    }

    // Obtener foros de una actividad por estado
    public List<Foro> obtenerPorActividadYEstado(Integer idActividad, Foro.Estado estado) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return foroRepository.findByIdActividadAndEstado(idActividad, estado);
    }

    // Buscar por titulo
    public List<Foro> buscarPorTitulo(String titulo) {
        return foroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Obtener por rango de fechas
    public List<Foro> obtenerPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return foroRepository.findByFechaPublicacionBetween(inicio, fin);
    }

    // Actualizar foro
    public Foro actualizarForo(Integer id, Foro datosNuevos) {
        Foro foroExistente = obtenerPorId(id);

        if (!foroExistente.getTitulo().equals(datosNuevos.getTitulo()) &&
                foroRepository.existsByTituloAndIdActividad(
                        datosNuevos.getTitulo(), foroExistente.getIdActividad())) {
            throw new RuntimeException("Ya existe un foro con el titulo: " + datosNuevos.getTitulo());
        }

        foroExistente.setTitulo(datosNuevos.getTitulo());
        foroExistente.setContenido(datosNuevos.getContenido());
        foroExistente.setEstado(datosNuevos.getEstado());
        return foroRepository.save(foroExistente);
    }

    // Cambiar estado del foro
    public Foro cambiarEstado(Integer id, Foro.Estado nuevoEstado) {
        Foro foro = obtenerPorId(id);
        foro.setEstado(nuevoEstado);
        return foroRepository.save(foro);
    }

    // Eliminar foro
    public void eliminarForo(Integer id) {
        if (!foroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Foro no encontrado con ID: " + id);
        }
        foroRepository.deleteById(id);
    }
}
