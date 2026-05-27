package com.centrocomunitario.controller;

import com.centrocomunitario.model.InscripcionPrograma;
import com.centrocomunitario.service.InscripcionProgramaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones-programa")
@CrossOrigin(origins = "*")
public class InscripcionProgramaController {

    @Autowired
    private InscripcionProgramaService inscripcionProgramaService;

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<InscripcionPrograma>> obtenerTodas() {
        return ResponseEntity.ok(inscripcionProgramaService.obtenerTodas());
    }

    // Obtener por clave compuesta
    @GetMapping("/{idUsuario}/{idPrograma}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idPrograma) {
        try {
            return ResponseEntity.ok(inscripcionProgramaService.obtenerPorId(idUsuario, idPrograma));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Inscripciones de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(inscripcionProgramaService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Inscritos en un programa
    @GetMapping("/programa/{idPrograma}")
    public ResponseEntity<?> obtenerPorPrograma(@PathVariable Integer idPrograma) {
        try {
            return ResponseEntity.ok(inscripcionProgramaService.obtenerPorPrograma(idPrograma));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Total inscritos
    @GetMapping("/programa/{idPrograma}/conteo")
    public ResponseEntity<?> contarInscritos(@PathVariable Integer idPrograma) {
        try {
            long total = inscripcionProgramaService.contarInscritos(idPrograma);
            return ResponseEntity.ok("Total inscritos en el programa ID " + idPrograma + ": " + total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(inscripcionProgramaService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Inscripciones sin fecha registrada
    @GetMapping("/sin-fecha")
    public ResponseEntity<List<InscripcionPrograma>> obtenerSinFecha() {
        return ResponseEntity.ok(inscripcionProgramaService.obtenerSinFecha());
    }

    // Inscribir usuario a programa
    @PostMapping
    public ResponseEntity<?> inscribir(@Valid @RequestBody InscripcionPrograma inscripcion) {
        try {
            InscripcionPrograma nueva = inscripcionProgramaService.inscribir(inscripcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar fecha
    @PatchMapping("/{idUsuario}/{idPrograma}/fecha")
    public ResponseEntity<?> actualizarFecha(@PathVariable Integer idUsuario,
                                             @PathVariable Integer idPrograma,
                                             @RequestParam LocalDate fecha) {
        try {
            return ResponseEntity.ok(inscripcionProgramaService.actualizarFecha(idUsuario, idPrograma, fecha));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Cancelar inscripcion
    @DeleteMapping("/{idUsuario}/{idPrograma}")
    public ResponseEntity<?> cancelarInscripcion(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idPrograma) {
        try {
            inscripcionProgramaService.cancelarInscripcion(idUsuario, idPrograma);
            return ResponseEntity.ok("Inscripcion del usuario ID " + idUsuario
                    + " en programa ID " + idPrograma + " cancelada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Cancelar todas de un usuario
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> cancelarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            inscripcionProgramaService.cancelarPorUsuario(idUsuario);
            return ResponseEntity.ok("Todas las inscripciones del usuario ID "
                    + idUsuario + " canceladas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Cancelar todas de un programa
    @DeleteMapping("/programa/{idPrograma}")
    public ResponseEntity<?> cancelarPorPrograma(@PathVariable Integer idPrograma) {
        try {
            inscripcionProgramaService.cancelarPorPrograma(idPrograma);
            return ResponseEntity.ok("Todas las inscripciones del programa ID "
                    + idPrograma + " canceladas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
