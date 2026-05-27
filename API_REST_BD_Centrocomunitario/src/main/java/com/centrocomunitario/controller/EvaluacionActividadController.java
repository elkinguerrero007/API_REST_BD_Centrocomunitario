package com.centrocomunitario.controller;

import com.centrocomunitario.model.EvaluacionActividad;
import com.centrocomunitario.service.EvaluacionActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin(origins = "*")
public class EvaluacionActividadController {

    @Autowired
    private EvaluacionActividadService evaluacionActividadService;

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<EvaluacionActividad>> obtenerTodas() {
        return ResponseEntity.ok(evaluacionActividadService.obtenerTodas());
    }

    // Obtener por clave compuesta
    @GetMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(evaluacionActividadService.obtenerPorId(idUsuario, idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Evaluaciones de una actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(evaluacionActividadService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Evaluaciones de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(evaluacionActividadService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Filtrar por puntuacion
    @GetMapping("/valoracion/{valoracion}")
    public ResponseEntity<?> obtenerPorValoracion(@PathVariable Integer valoracion) {
        try {
            return ResponseEntity.ok(evaluacionActividadService.obtenerPorValoracion(valoracion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Evaluaciones de actividad por valoracion
    @GetMapping("/actividad/{idActividad}/valoracion/{valoracion}")
    public ResponseEntity<?> obtenerPorActividadYValoracion(@PathVariable Integer idActividad,
                                                             @PathVariable Integer valoracion) {
        try {
            return ResponseEntity.ok(evaluacionActividadService
                    .obtenerPorActividadYValoracion(idActividad, valoracion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Promedio de valoracion
    @GetMapping("/actividad/{idActividad}/promedio")
    public ResponseEntity<?> obtenerPromedio(@PathVariable Integer idActividad) {
        try {
            Double promedio = evaluacionActividadService.obtenerPromedioValoracion(idActividad);
            return ResponseEntity.ok("Promedio de valoracion de la actividad ID "
                    + idActividad + ": " + promedio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Filtrar por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDateTime inicio,
                                                    @RequestParam LocalDateTime fin) {
        try {
            return ResponseEntity.ok(evaluacionActividadService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Crear evaluacion
    @PostMapping
    public ResponseEntity<?> crearEvaluacion(@Valid @RequestBody EvaluacionActividad evaluacion) {
        try {
            EvaluacionActividad nueva = evaluacionActividadService.crearEvaluacion(evaluacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar evaluacion
    @PutMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<?> actualizarEvaluacion(@PathVariable Integer idUsuario,
                                                   @PathVariable Integer idActividad,
                                                   @Valid @RequestBody EvaluacionActividad evaluacion) {
        try {
            return ResponseEntity.ok(evaluacionActividadService
                    .actualizarEvaluacion(idUsuario, idActividad, evaluacion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar evaluacion especifica
    @DeleteMapping("/{idUsuario}/{idActividad}")
    public ResponseEntity<?> eliminarEvaluacion(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idActividad) {
        try {
            evaluacionActividadService.eliminarEvaluacion(idUsuario, idActividad);
            return ResponseEntity.ok("Evaluacion del usuario ID " + idUsuario
                    + " para actividad ID " + idActividad + " eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todas de una actividad
    @DeleteMapping("/actividad/{idActividad}")
    public ResponseEntity<?> eliminarPorActividad(@PathVariable Integer idActividad) {
        try {
            evaluacionActividadService.eliminarPorActividad(idActividad);
            return ResponseEntity.ok("Todas las evaluaciones de la actividad ID "
                    + idActividad + " eliminadas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todas de un usuario
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> eliminarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            evaluacionActividadService.eliminarPorUsuario(idUsuario);
            return ResponseEntity.ok("Todas las evaluaciones del usuario ID "
                    + idUsuario + " eliminadas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
