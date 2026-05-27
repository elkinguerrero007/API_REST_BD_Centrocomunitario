package com.centrocomunitario.controller;

import com.centrocomunitario.model.SeguimientoActividad;
import com.centrocomunitario.service.SeguimientoActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
@CrossOrigin(origins = "*")
public class SeguimientoActividadController {

    @Autowired
    private SeguimientoActividadService seguimientoActividadService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<SeguimientoActividad>> obtenerTodos() {
        return ResponseEntity.ok(seguimientoActividadService.obtenerTodos());
    }

    // Obtener por clave compuesta
    @GetMapping("/{idUsuario}/{idInstructor}/{idParticipante}/{idActividad}/{fechaRegistro}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUsuario,
                                           @PathVariable Integer idInstructor,
                                           @PathVariable Integer idParticipante,
                                           @PathVariable Integer idActividad,
                                           @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaRegistro) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.obtenerPorId(
                    idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por instructor
    @GetMapping("/instructor/{idInstructor}")
    public ResponseEntity<?> obtenerPorInstructor(@PathVariable Integer idInstructor) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.obtenerPorInstructor(idInstructor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por participante
    @GetMapping("/participante/{idParticipante}")
    public ResponseEntity<?> obtenerPorParticipante(@PathVariable Integer idParticipante) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.obtenerPorParticipante(idParticipante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por instructor y actividad
    @GetMapping("/instructor/{idInstructor}/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorInstructorYActividad(@PathVariable Integer idInstructor,
                                                             @PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(seguimientoActividadService
                    .obtenerPorInstructorYActividad(idInstructor, idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por participante y actividad
    @GetMapping("/participante/{idParticipante}/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorParticipanteYActividad(@PathVariable Integer idParticipante,
                                                               @PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(seguimientoActividadService
                    .obtenerPorParticipanteYActividad(idParticipante, idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por instructor y participante
    @GetMapping("/instructor/{idInstructor}/participante/{idParticipante}")
    public ResponseEntity<?> obtenerPorInstructorYParticipante(@PathVariable Integer idInstructor,
                                                                @PathVariable Integer idParticipante) {
        try {
            return ResponseEntity.ok(seguimientoActividadService
                    .obtenerPorInstructorYParticipante(idInstructor, idParticipante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por nivel de progreso
    @GetMapping("/progreso/{nivelProgreso}")
    public ResponseEntity<List<SeguimientoActividad>> obtenerPorNivelProgreso(
            @PathVariable String nivelProgreso) {
        return ResponseEntity.ok(seguimientoActividadService.obtenerPorNivelProgreso(nivelProgreso));
    }

    // Buscar por aspecto
    @GetMapping("/aspecto")
    public ResponseEntity<List<SeguimientoActividad>> buscarPorAspecto(@RequestParam String valor) {
        return ResponseEntity.ok(seguimientoActividadService.buscarPorAspecto(valor));
    }

    // Obtener por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Actualizar seguimiento
    @PutMapping("/{idUsuario}/{idInstructor}/{idParticipante}/{idActividad}/{fechaRegistro}")
    public ResponseEntity<?> actualizarSeguimiento(@PathVariable Integer idUsuario,
                                                    @PathVariable Integer idInstructor,
                                                    @PathVariable Integer idParticipante,
                                                    @PathVariable Integer idActividad,
                                                    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaRegistro,
                                                    @Valid @RequestBody SeguimientoActividad datosNuevos) {
        try {
            return ResponseEntity.ok(seguimientoActividadService.actualizarSeguimiento(
                    idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro, datosNuevos));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Registrar seguimiento
    @PostMapping
    public ResponseEntity<?> registrarSeguimiento(@Valid @RequestBody SeguimientoActividad seguimiento) {
        try {
            SeguimientoActividad nuevo = seguimientoActividadService.registrarSeguimiento(seguimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar seguimiento
    @DeleteMapping("/{idUsuario}/{idInstructor}/{idParticipante}/{idActividad}/{fechaRegistro}")
    public ResponseEntity<?> eliminarSeguimiento(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idInstructor,
                                                  @PathVariable Integer idParticipante,
                                                  @PathVariable Integer idActividad,
                                                  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaRegistro) {
        try {
            seguimientoActividadService.eliminarSeguimiento(idUsuario, idInstructor,
                    idParticipante, idActividad, fechaRegistro);
            return ResponseEntity.ok("Seguimiento eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
