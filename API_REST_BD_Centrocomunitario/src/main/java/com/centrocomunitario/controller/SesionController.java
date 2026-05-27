package com.centrocomunitario.controller;

import com.centrocomunitario.model.Sesion;
import com.centrocomunitario.service.SesionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin(origins = "*")
public class SesionController {

    @Autowired
    private SesionService sesionService;

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<Sesion>> obtenerTodas() {
        return ResponseEntity.ok(sesionService.obtenerTodas());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(sesionService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Sesiones de una actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(sesionService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Sesiones en un espacio
    @GetMapping("/espacio/{idEspacio}")
    public ResponseEntity<?> obtenerPorEspacio(@PathVariable Integer idEspacio) {
        try {
            return ResponseEntity.ok(sesionService.obtenerPorEspacio(idEspacio));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Sesiones en una fecha exacta
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Sesion>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(sesionService.obtenerPorFecha(fecha));
    }

    // Sesiones en un rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(sesionService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Sesiones por modalidad
    @GetMapping("/modalidad/{modalidad}")
    public ResponseEntity<?> obtenerPorModalidad(@PathVariable String modalidad) {
        try {
            Sesion.Modalidad modalidadEnum = Sesion.Modalidad.valueOf(modalidad);
            return ResponseEntity.ok(sesionService.obtenerPorModalidad(modalidadEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Modalidad no valida. Use: presencial, virtual, hibrida");
        }
    }

    // Sesiones de actividad por fecha
    @GetMapping("/actividad/{idActividad}/fecha/{fecha}")
    public ResponseEntity<?> obtenerPorActividadYFecha(@PathVariable Integer idActividad,
                                                        @PathVariable LocalDate fecha) {
        try {
            return ResponseEntity.ok(sesionService.obtenerPorActividadYFecha(idActividad, fecha));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Disponibilidad de espacio en una fecha
    @GetMapping("/espacio/{idEspacio}/fecha/{fecha}")
    public ResponseEntity<?> obtenerPorEspacioYFecha(@PathVariable Integer idEspacio,
                                                      @PathVariable LocalDate fecha) {
        try {
            return ResponseEntity.ok(sesionService.obtenerPorEspacioYFecha(idEspacio, fecha));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por tema
    @GetMapping("/buscar")
    public ResponseEntity<List<Sesion>> buscarPorTema(@RequestParam String tema) {
        return ResponseEntity.ok(sesionService.buscarPorTema(tema));
    }

    // Crear sesion
    @PostMapping
    public ResponseEntity<?> crearSesion(@Valid @RequestBody Sesion sesion) {
        try {
            Sesion nueva = sesionService.crearSesion(sesion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar sesion
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSesion(@PathVariable Integer id,
                                              @Valid @RequestBody Sesion sesion) {
        try {
            return ResponseEntity.ok(sesionService.actualizarSesion(id, sesion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar sesion
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSesion(@PathVariable Integer id) {
        try {
            sesionService.eliminarSesion(id);
            return ResponseEntity.ok("Sesion con ID " + id + " eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
