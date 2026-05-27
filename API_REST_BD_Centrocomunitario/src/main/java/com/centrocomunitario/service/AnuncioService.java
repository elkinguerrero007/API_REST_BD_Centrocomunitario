package com.centrocomunitario.service;

import com.centrocomunitario.model.Anuncio;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.AnuncioRepository;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    // Crear anuncio
    public Anuncio crearAnuncio(Anuncio anuncio) {
        if (!usuarioRepository.existsById(anuncio.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + anuncio.getIdUsuario());
        }
        if (anuncio.getIdActividad() != null &&
                !actividadRepository.existsById(anuncio.getIdActividad())) {
            throw new RuntimeException("Actividad no encontrada con ID: " + anuncio.getIdActividad());
        }
        return anuncioRepository.save(anuncio);
    }

    // Obtener todos los anuncios
    public List<Anuncio> obtenerTodos() {
        return anuncioRepository.findAll();
    }

    // Buscar por ID
    public Anuncio obtenerPorId(Integer id) {
        return anuncioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anuncio no encontrado con ID: " + id));
    }

    // Obtener anuncios de un usuario
    public List<Anuncio> obtenerPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return anuncioRepository.findByIdUsuario(idUsuario);
    }

    // Obtener anuncios de una actividad
    public List<Anuncio> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return anuncioRepository.findByIdActividad(idActividad);
    }

    // Obtener anuncios generales (sin actividad)
    public List<Anuncio> obtenerGenerales() {
        return anuncioRepository.findByIdActividadIsNull();
    }

    // Buscar por titulo
    public List<Anuncio> buscarPorTitulo(String titulo) {
        return anuncioRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Obtener por fecha exacta
    public List<Anuncio> obtenerPorFecha(LocalDate fecha) {
        return anuncioRepository.findByFecha(fecha);
    }

    // Obtener por rango de fechas
    public List<Anuncio> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return anuncioRepository.findByFechaBetween(inicio, fin);
    }

    // Obtener anuncios vigentes (fecha >= hoy)
    public List<Anuncio> obtenerVigentes() {
        return anuncioRepository.findByFechaGreaterThanEqual(LocalDate.now());
    }

    // Actualizar anuncio
    public Anuncio actualizarAnuncio(Integer id, Anuncio datosNuevos) {
        Anuncio existente = obtenerPorId(id);

        if (datosNuevos.getIdActividad() != null &&
                !actividadRepository.existsById(datosNuevos.getIdActividad())) {
            throw new RuntimeException("Actividad no encontrada con ID: " + datosNuevos.getIdActividad());
        }

        existente.setTitulo(datosNuevos.getTitulo());
        existente.setContenido(datosNuevos.getContenido());
        existente.setFecha(datosNuevos.getFecha());
        existente.setIdActividad(datosNuevos.getIdActividad());
        return anuncioRepository.save(existente);
    }

    // Eliminar anuncio
    public void eliminarAnuncio(Integer id) {
        if (!anuncioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Anuncio no encontrado con ID: " + id);
        }
        anuncioRepository.deleteById(id);
    }
}
