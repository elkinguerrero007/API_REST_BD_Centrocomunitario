package com.centrocomunitario.controller;

import com.centrocomunitario.model.ActividadRecurso;
import com.centrocomunitario.service.ActividadRecursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividad-recursos")
@CrossOrigin(origins = "*")
public class ActividadRecursoController {

    @Autowired
    private ActividadRecursoService actividadRecursoService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ActividadRecurso>> obtenerTodos() {
        return ResponseEntity.ok(actividadRecursoService.obtenerTodos());
    }

    // Recursos de una actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(actividadRecursoService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por palabra clave
    @GetMapping("/buscar")
    public ResponseEntity<List<ActividadRecurso>> buscarPorRecurso(@RequestParam String recurso) {
        return ResponseEntity.ok(actividadRecursoService.buscarPorRecurso(recurso));
    }

    // Obtener por clave compuesta
    @GetMapping("/{idActividad}/{recursoRequerido}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idActividad,
                                          @PathVariable String recursoRequerido) {
        try {
            return ResponseEntity.ok(actividadRecursoService.obtenerPorId(idActividad, recursoRequerido));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Agregar recurso a actividad
    @PostMapping
    public ResponseEntity<?> agregarRecurso(@Valid @RequestBody ActividadRecurso recurso) {
        try {
            ActividadRecurso nuevo = actividadRecursoService.agregarRecurso(recurso);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar recurso especifico
    @DeleteMapping("/{idActividad}/{recursoRequerido}")
    public ResponseEntity<?> eliminarRecurso(@PathVariable Integer idActividad,
                                             @PathVariable String recursoRequerido) {
        try {
            actividadRecursoService.eliminarRecurso(idActividad, recursoRequerido);
            return ResponseEntity.ok("Recurso '" + recursoRequerido
                    + "' eliminado de la actividad con ID: " + idActividad);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todos los recursos de una actividad
    @DeleteMapping("/actividad/{idActividad}")
    public ResponseEntity<?> eliminarRecursosPorActividad(@PathVariable Integer idActividad) {
        try {
            actividadRecursoService.eliminarRecursosPorActividad(idActividad);
            return ResponseEntity.ok("Todos los recursos de la actividad con ID "
                    + idActividad + " eliminados correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
