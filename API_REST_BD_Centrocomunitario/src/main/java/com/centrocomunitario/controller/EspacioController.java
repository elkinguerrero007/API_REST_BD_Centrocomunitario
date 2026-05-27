package com.centrocomunitario.controller;

import com.centrocomunitario.model.Espacio;
import com.centrocomunitario.service.EspacioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/espacios")
@CrossOrigin(origins = "*")
public class EspacioController {

    @Autowired
    private EspacioService espacioService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Espacio>> obtenerTodos() {
        return ResponseEntity.ok(espacioService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(espacioService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(espacioService.obtenerPorNombre(nombre));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por palabra clave
    @GetMapping("/buscar")
    public ResponseEntity<List<Espacio>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(espacioService.buscarPorNombre(nombre));
    }

    // Obtener por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String estado) {
        try {
            Espacio.Estado estadoEnum = Espacio.Estado.valueOf(estado);
            return ResponseEntity.ok(espacioService.obtenerPorEstado(estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: habilitado, inhabilitado");
        }
    }

    // Obtener solo habilitados
    @GetMapping("/habilitados")
    public ResponseEntity<List<Espacio>> obtenerHabilitados() {
        return ResponseEntity.ok(espacioService.obtenerHabilitados());
    }

    // Obtener por capacidad minima
    @GetMapping("/capacidad")
    public ResponseEntity<?> obtenerPorCapacidad(@RequestParam Integer minima) {
        try {
            return ResponseEntity.ok(espacioService.obtenerPorCapacidadMinima(minima));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Buscar por ubicacion
    @GetMapping("/ubicacion")
    public ResponseEntity<List<Espacio>> buscarPorUbicacion(@RequestParam String nombre) {
        return ResponseEntity.ok(espacioService.buscarPorUbicacion(nombre));
    }

    // Crear espacio
    @PostMapping
    public ResponseEntity<?> crearEspacio(@Valid @RequestBody Espacio espacio) {
        try {
            Espacio nuevo = espacioService.crearEspacio(espacio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar espacio
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEspacio(@PathVariable Integer id,
                                               @Valid @RequestBody Espacio espacio) {
        try {
            return ResponseEntity.ok(espacioService.actualizarEspacio(id, espacio));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Cambiar estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id,
                                           @RequestParam String estado) {
        try {
            Espacio.Estado estadoEnum = Espacio.Estado.valueOf(estado);
            return ResponseEntity.ok(espacioService.cambiarEstado(id, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: habilitado, inhabilitado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar espacio
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEspacio(@PathVariable Integer id) {
        try {
            espacioService.eliminarEspacio(id);
            return ResponseEntity.ok("Espacio con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
