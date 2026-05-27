package com.centrocomunitario.controller;

import com.centrocomunitario.model.AsistenciaSesion;
import com.centrocomunitario.service.AsistenciaSesionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaSesionController {

    @Autowired
    private AsistenciaSesionService asistenciaSesionService;

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<AsistenciaSesion>> obtenerTodas() {
        return ResponseEntity.ok(asistenciaSesionService.obtenerTodas());
    }

    // Obtener por clave compuesta
    @GetMapping("/{idUsuario}/{idSesion}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idSesion) {
        try {
            return ResponseEntity.ok(asistenciaSesionService.obtenerPorId(idUsuario, idSesion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Asistencias de una sesion
    @GetMapping("/sesion/{idSesion}")
    public ResponseEntity<?> obtenerPorSesion(@PathVariable Integer idSesion) {
        try {
            return ResponseEntity.ok(asistenciaSesionService.obtenerPorSesion(idSesion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Asistencias de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(asistenciaSesionService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Filtrar por estado
    @GetMapping("/estado")
    public ResponseEntity<?> obtenerPorEstado(@RequestParam String valor) {
        try {
            return ResponseEntity.ok(asistenciaSesionService.obtenerPorEstado(valor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Asistencias de sesion por estado
    @GetMapping("/sesion/{idSesion}/estado")
    public ResponseEntity<?> obtenerPorSesionYEstado(@PathVariable Integer idSesion,
                                                      @RequestParam String valor) {
        try {
            return ResponseEntity.ok(asistenciaSesionService.obtenerPorSesionYEstado(idSesion, valor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Contar asistentes de una sesion
    @GetMapping("/sesion/{idSesion}/conteo")
    public ResponseEntity<?> contarAsistentes(@PathVariable Integer idSesion) {
        try {
            long total = asistenciaSesionService.contarAsistentes(idSesion);
            return ResponseEntity.ok("Total de asistentes en la sesion " + idSesion + ": " + total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Registrar asistencia
    @PostMapping
    public ResponseEntity<?> registrarAsistencia(@Valid @RequestBody AsistenciaSesion asistencia) {
        try {
            AsistenciaSesion nueva = asistenciaSesionService.registrarAsistencia(asistencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar asistencia
    @PutMapping("/{idUsuario}/{idSesion}")
    public ResponseEntity<?> actualizarAsistencia(@PathVariable Integer idUsuario,
                                                   @PathVariable Integer idSesion,
                                                   @Valid @RequestBody AsistenciaSesion asistencia) {
        try {
            return ResponseEntity.ok(asistenciaSesionService.actualizarAsistencia(idUsuario, idSesion, asistencia));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar asistencia especifica
    @DeleteMapping("/{idUsuario}/{idSesion}")
    public ResponseEntity<?> eliminarAsistencia(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idSesion) {
        try {
            asistenciaSesionService.eliminarAsistencia(idUsuario, idSesion);
            return ResponseEntity.ok("Asistencia del usuario ID " + idUsuario
                    + " en sesion ID " + idSesion + " eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todas las asistencias de una sesion
    @DeleteMapping("/sesion/{idSesion}")
    public ResponseEntity<?> eliminarPorSesion(@PathVariable Integer idSesion) {
        try {
            asistenciaSesionService.eliminarPorSesion(idSesion);
            return ResponseEntity.ok("Todas las asistencias de la sesion ID "
                    + idSesion + " eliminadas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todas las asistencias de un usuario
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> eliminarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            asistenciaSesionService.eliminarPorUsuario(idUsuario);
            return ResponseEntity.ok("Todas las asistencias del usuario ID "
                    + idUsuario + " eliminadas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
