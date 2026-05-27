package com.centrocomunitario.controller;

import com.centrocomunitario.model.InscripcionActividad;
import com.centrocomunitario.service.InscripcionActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones-actividad")
@CrossOrigin(origins = "*")
public class InscripcionActividadController {

    @Autowired
    private InscripcionActividadService inscripcionActividadService;

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<InscripcionActividad>> obtenerTodas() {
        return ResponseEntity.ok(inscripcionActividadService.obtenerTodas());
    }

    // Obtener por clave compuesta
    @GetMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(inscripcionActividadService.obtenerPorId(idUsuario, idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Inscripciones de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(inscripcionActividadService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Inscritos en una actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(inscripcionActividadService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Filtrar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String estado) {
        try {
            InscripcionActividad.Estado estadoEnum = InscripcionActividad.Estado.valueOf(estado);
            return ResponseEntity.ok(inscripcionActividadService.obtenerPorEstado(estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: inscrito, matriculado, retirado");
        }
    }

    // Inscripciones de actividad por estado
    @GetMapping("/actividad/{idActividad}/estado/{estado}")
    public ResponseEntity<?> obtenerPorActividadYEstado(@PathVariable Integer idActividad,
                                                         @PathVariable String estado) {
        try {
            InscripcionActividad.Estado estadoEnum = InscripcionActividad.Estado.valueOf(estado);
            return ResponseEntity.ok(inscripcionActividadService
                    .obtenerPorActividadYEstado(idActividad, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: inscrito, matriculado, retirado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Inscripciones de usuario por estado
    @GetMapping("/usuario/{idUsuario}/estado/{estado}")
    public ResponseEntity<?> obtenerPorUsuarioYEstado(@PathVariable Integer idUsuario,
                                                       @PathVariable String estado) {
        try {
            InscripcionActividad.Estado estadoEnum = InscripcionActividad.Estado.valueOf(estado);
            return ResponseEntity.ok(inscripcionActividadService
                    .obtenerPorUsuarioYEstado(idUsuario, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: inscrito, matriculado, retirado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Total inscritos
    @GetMapping("/actividad/{idActividad}/conteo")
    public ResponseEntity<?> contarInscritos(@PathVariable Integer idActividad) {
        try {
            long total = inscripcionActividadService.contarInscritos(idActividad);
            return ResponseEntity.ok("Total inscritos en actividad ID " + idActividad + ": " + total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Contar inscritos por estado
    @GetMapping("/actividad/{idActividad}/conteo/{estado}")
    public ResponseEntity<?> contarPorEstado(@PathVariable Integer idActividad,
                                              @PathVariable String estado) {
        try {
            InscripcionActividad.Estado estadoEnum = InscripcionActividad.Estado.valueOf(estado);
            long total = inscripcionActividadService.contarPorEstado(idActividad, estadoEnum);
            return ResponseEntity.ok("Total " + estado + " en actividad ID " + idActividad + ": " + total);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: inscrito, matriculado, retirado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(inscripcionActividadService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Inscribir usuario
    @PostMapping
    public ResponseEntity<?> inscribir(@Valid @RequestBody InscripcionActividad inscripcion) {
        try {
            InscripcionActividad nueva = inscripcionActividadService.inscribir(inscripcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Cambiar estado
    @PatchMapping("/{idUsuario}/{idActividad}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer idUsuario,
                                           @PathVariable Integer idActividad,
                                           @RequestParam String estado) {
        try {
            InscripcionActividad.Estado estadoEnum = InscripcionActividad.Estado.valueOf(estado);
            return ResponseEntity.ok(inscripcionActividadService
                    .cambiarEstado(idUsuario, idActividad, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: inscrito, matriculado, retirado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Cancelar inscripcion
    @DeleteMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<?> cancelarInscripcion(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idActividad) {
        try {
            boolean borradoLogico = inscripcionActividadService.cancelarInscripcion(idUsuario, idActividad);
            if (borradoLogico) {
                return ResponseEntity.ok("El participante ID " + idUsuario
                        + " tiene historial en la actividad ID " + idActividad
                        + ". Se aplico borrado logico: estado cambiado a 'retirado'.");
            } else {
                return ResponseEntity.ok("Inscripcion del usuario ID " + idUsuario
                        + " en actividad ID " + idActividad + " eliminada correctamente.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Cancelar todas de un usuario
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> cancelarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            inscripcionActividadService.cancelarPorUsuario(idUsuario);
            return ResponseEntity.ok("Todas las inscripciones del usuario ID "
                    + idUsuario + " canceladas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Cancelar todas de una actividad
    @DeleteMapping("/actividad/{idActividad}")
    public ResponseEntity<?> cancelarPorActividad(@PathVariable Integer idActividad) {
        try {
            inscripcionActividadService.cancelarPorActividad(idActividad);
            return ResponseEntity.ok("Todas las inscripciones de la actividad ID "
                    + idActividad + " canceladas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
