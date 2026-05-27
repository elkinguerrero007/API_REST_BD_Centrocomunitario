package com.centrocomunitario.service;

import com.centrocomunitario.model.Espacio;
import com.centrocomunitario.repository.EspacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacioService {

    @Autowired
    private EspacioRepository espacioRepository;

    // Crear espacio
    public Espacio crearEspacio(Espacio espacio) {
        if (espacioRepository.existsByNombre(espacio.getNombre())) {
            throw new RuntimeException("Ya existe un espacio con el nombre: " + espacio.getNombre());
        }
        return espacioRepository.save(espacio);
    }

    // Obtener todos los espacios
    public List<Espacio> obtenerTodos() {
        return espacioRepository.findAll();
    }

    // Buscar por ID
    public Espacio obtenerPorId(Integer id) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + id));
    }

    // Buscar por nombre exacto
    public Espacio obtenerPorNombre(String nombre) {
        return espacioRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con nombre: " + nombre));
    }

    // Buscar por palabra clave en el nombre
    public List<Espacio> buscarPorNombre(String nombre) {
        return espacioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar por estado
    public List<Espacio> obtenerPorEstado(Espacio.Estado estado) {
        return espacioRepository.findByEstado(estado);
    }

    // Buscar espacios habilitados
    public List<Espacio> obtenerHabilitados() {
        return espacioRepository.findByEstado(Espacio.Estado.habilitado);
    }

    // Buscar por capacidad minima
    public List<Espacio> obtenerPorCapacidadMinima(Integer capacidad) {
        if (capacidad < 1) {
            throw new RuntimeException("La capacidad minima debe ser al menos 1");
        }
        return espacioRepository.findByCapacidadGreaterThanEqual(capacidad);
    }

    // Buscar por ubicacion
    public List<Espacio> buscarPorUbicacion(String ubicacion) {
        return espacioRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }

    // Actualizar espacio
    public Espacio actualizarEspacio(Integer id, Espacio datosNuevos) {
        Espacio espacioExistente = obtenerPorId(id);

        if (!espacioExistente.getNombre().equals(datosNuevos.getNombre()) &&
                espacioRepository.existsByNombre(datosNuevos.getNombre())) {
            throw new RuntimeException("Ya existe un espacio con el nombre: " + datosNuevos.getNombre());
        }

        espacioExistente.setNombre(datosNuevos.getNombre());
        espacioExistente.setUbicacion(datosNuevos.getUbicacion());
        espacioExistente.setCapacidad(datosNuevos.getCapacidad());
        espacioExistente.setDescripcion(datosNuevos.getDescripcion());
        espacioExistente.setEstado(datosNuevos.getEstado());

        return espacioRepository.save(espacioExistente);
    }

    // Cambiar estado del espacio
    public Espacio cambiarEstado(Integer id, Espacio.Estado nuevoEstado) {
        Espacio espacio = obtenerPorId(id);
        espacio.setEstado(nuevoEstado);
        return espacioRepository.save(espacio);
    }

    // Eliminar espacio
    public void eliminarEspacio(Integer id) {
        if (!espacioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Espacio no encontrado con ID: " + id);
        }
        espacioRepository.deleteById(id);
    }
}
