package com.centrocomunitario.service;

import com.centrocomunitario.model.Sesion;
import com.centrocomunitario.repository.ActividadRepository;
import com.centrocomunitario.repository.EspacioRepository;
import com.centrocomunitario.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private EspacioRepository espacioRepository;

    // Crear sesion
    public Sesion crearSesion(Sesion sesion) {
        validarRelaciones(sesion);
        validarHoras(sesion);
        return sesionRepository.save(sesion);
    }

    // Obtener todas las sesiones
    public List<Sesion> obtenerTodas() {
        return sesionRepository.findAll();
    }

    // Buscar por ID
    public Sesion obtenerPorId(Integer id) {
        return sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada con ID: " + id));
    }

    // Buscar por actividad
    public List<Sesion> obtenerPorActividad(Integer idActividad) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return sesionRepository.findByIdActividad(idActividad);
    }

    // Buscar por espacio
    public List<Sesion> obtenerPorEspacio(Integer idEspacio) {
        if (!espacioRepository.existsById(idEspacio)) {
            throw new RuntimeException("Espacio no encontrado con ID: " + idEspacio);
        }
        return sesionRepository.findByIdEspacio(idEspacio);
    }

    // Buscar por fecha exacta
    public List<Sesion> obtenerPorFecha(LocalDate fecha) {
        return sesionRepository.findByFecha(fecha);
    }

    // Buscar por rango de fechas
    public List<Sesion> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        return sesionRepository.findByFechaBetween(inicio, fin);
    }

    // Buscar por modalidad
    public List<Sesion> obtenerPorModalidad(Sesion.Modalidad modalidad) {
        return sesionRepository.findByModalidad(modalidad);
    }

    // Buscar por actividad y fecha
    public List<Sesion> obtenerPorActividadYFecha(Integer idActividad, LocalDate fecha) {
        if (!actividadRepository.existsById(idActividad)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + idActividad);
        }
        return sesionRepository.findByIdActividadAndFecha(idActividad, fecha);
    }

    // Buscar por espacio y fecha (verificar disponibilidad)
    public List<Sesion> obtenerPorEspacioYFecha(Integer idEspacio, LocalDate fecha) {
        if (!espacioRepository.existsById(idEspacio)) {
            throw new RuntimeException("Espacio no encontrado con ID: " + idEspacio);
        }
        return sesionRepository.findByIdEspacioAndFecha(idEspacio, fecha);
    }

    // Buscar por tema
    public List<Sesion> buscarPorTema(String tema) {
        return sesionRepository.findByTemaContainingIgnoreCase(tema);
    }

    // Actualizar sesion
    public Sesion actualizarSesion(Integer id, Sesion datosNuevos) {
        Sesion sesionExistente = obtenerPorId(id);
        validarRelaciones(datosNuevos);
        validarHoras(datosNuevos);

        sesionExistente.setIdActividad(datosNuevos.getIdActividad());
        sesionExistente.setIdEspacio(datosNuevos.getIdEspacio());
        sesionExistente.setFecha(datosNuevos.getFecha());
        sesionExistente.setHoraInicio(datosNuevos.getHoraInicio());
        sesionExistente.setHoraFinalizacion(datosNuevos.getHoraFinalizacion());
        sesionExistente.setModalidad(datosNuevos.getModalidad());
        sesionExistente.setEnlaceAcceso(datosNuevos.getEnlaceAcceso());
        sesionExistente.setTema(datosNuevos.getTema());

        return sesionRepository.save(sesionExistente);
    }

    // Eliminar sesion
    public void eliminarSesion(Integer id) {
        if (!sesionRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Sesion no encontrada con ID: " + id);
        }
        sesionRepository.deleteById(id);
    }

    // Validar que las FK existen en la BD
    private void validarRelaciones(Sesion sesion) {
        if (!actividadRepository.existsById(sesion.getIdActividad())) {
            throw new RuntimeException("Actividad no encontrada con ID: " + sesion.getIdActividad());
        }
        if (!espacioRepository.existsById(sesion.getIdEspacio())) {
            throw new RuntimeException("Espacio no encontrado con ID: " + sesion.getIdEspacio());
        }
    }

    // Validar que hora fin sea posterior a hora inicio
    private void validarHoras(Sesion sesion) {
        if (sesion.getHoraFinalizacion().isBefore(sesion.getHoraInicio()) ||
                sesion.getHoraFinalizacion().equals(sesion.getHoraInicio())) {
            throw new RuntimeException("La hora de finalizacion debe ser posterior a la hora de inicio");
        }
    }
}
