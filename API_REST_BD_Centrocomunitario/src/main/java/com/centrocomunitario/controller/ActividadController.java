package com.centrocomunitario.controller;

import com.centrocomunitario.model.Actividad;
import com.centrocomunitario.service.ActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@CrossOrigin(origins = "*")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    //obtener todas
    @GetMapping
    public ResponseEntity<List<Actividad>> obtenerTodas() {
        return ResponseEntity.ok(actividadService.obtenerTodas());
    }

    //obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //buscar por palabra clave
    @GetMapping("/buscar")
    public ResponseEntity<List<Actividad>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(actividadService.buscarPorNombre(nombre));
    }

    //filtrar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String estado) {
        try {
            Actividad.Estado estadoEnum = Actividad.Estado.valueOf(estado);
            return ResponseEntity.ok(actividadService.obtenerPorEstado(estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: programada, en_curso, finalizada, cancelada");
        }
    }

    //filtrar por categoria
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> obtenerPorCategoria(@PathVariable Integer idCategoria) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorCategoria(idCategoria));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //filtrar por programa
    @GetMapping("/programa/{idPrograma}")
    public ResponseEntity<?> obtenerPorPrograma(@PathVariable Integer idPrograma) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorPrograma(idPrograma));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //filtrar por instructor
    @GetMapping("/instructor/{idInstructor}")
    public ResponseEntity<?> obtenerPorInstructor(@PathVariable Integer idInstructor) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorInstructor(idInstructor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //filtrar por proponente
    @GetMapping("/proponente/{idProponente}")
    public ResponseEntity<?> obtenerPorProponente(@PathVariable Integer idProponente) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorProponente(idProponente));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //filtrar por aprobador
    @GetMapping("/aprobador/{idAprobador}")
    public ResponseEntity<?> obtenerPorAprobador(@PathVariable Integer idAprobador) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorAprobador(idAprobador));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // actividades aun no finalizadas
    @GetMapping("/vigentes")
    public ResponseEntity<List<Actividad>> obtenerVigentes() {
        return ResponseEntity.ok(actividadService.obtenerVigentes());
    }

    // filtrar por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(actividadService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //crear actividad
    @PostMapping
    public ResponseEntity<?> crearActividad(@Valid @RequestBody Actividad actividad) {
        try {
            Actividad nueva = actividadService.crearActividad(actividad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //actualizar actividad
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarActividad(@PathVariable Integer id,
                                                 @Valid @RequestBody Actividad actividad) {
        try {
            return ResponseEntity.ok(actividadService.actualizarActividad(id, actividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // cambiar solo el estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id,
                                           @RequestParam String estado) {
        try {
            Actividad.Estado estadoEnum = Actividad.Estado.valueOf(estado);
            return ResponseEntity.ok(actividadService.cambiarEstado(id, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: programada, en_curso, finalizada, cancelada");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // eliminar actividad (borrado lógico si tiene dependencias)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarActividad(@PathVariable Integer id) {
        try {
            boolean borradoLogico = actividadService.eliminarActividad(id);
            if (borradoLogico) {
                return ResponseEntity.ok("La actividad ID " + id
                        + " tiene participantes inscritos o sesiones asignadas. "
                        + "Se aplico borrado logico: estado cambiado a 'cancelada'.");
            } else {
                return ResponseEntity.ok("Actividad con ID " + id + " eliminada correctamente.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
