package com.centrocomunitario.controller;

import com.centrocomunitario.model.ResponsablePrograma;
import com.centrocomunitario.service.ResponsableProgramaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsables-programa")
@CrossOrigin(origins = "*")
public class ResponsableProgramaController {

    @Autowired
    private ResponsableProgramaService responsableProgramaService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ResponsablePrograma>> obtenerTodos() {
        return ResponseEntity.ok(responsableProgramaService.obtenerTodos());
    }

    // Obtener por clave compuesta
    @GetMapping("/{idUsuario}/{idPrograma}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idPrograma) {
        try {
            return ResponseEntity.ok(responsableProgramaService.obtenerPorId(idUsuario, idPrograma));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Programas de un responsable
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(responsableProgramaService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Responsables de un programa
    @GetMapping("/programa/{idPrograma}")
    public ResponseEntity<?> obtenerPorPrograma(@PathVariable Integer idPrograma) {
        try {
            return ResponseEntity.ok(responsableProgramaService.obtenerPorPrograma(idPrograma));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Total responsables
    @GetMapping("/programa/{idPrograma}/conteo")
    public ResponseEntity<?> contarResponsables(@PathVariable Integer idPrograma) {
        try {
            long total = responsableProgramaService.contarResponsables(idPrograma);
            return ResponseEntity.ok("Total de responsables del programa ID " + idPrograma + ": " + total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Asignar responsable a programa
    @PostMapping
    public ResponseEntity<?> asignarResponsable(@Valid @RequestBody ResponsablePrograma responsable) {
        try {
            ResponsablePrograma nuevo = responsableProgramaService.asignarResponsable(responsable);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar responsable
    @DeleteMapping("/{idUsuario}/{idPrograma}")
    public ResponseEntity<?> eliminarResponsable(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idPrograma) {
        try {
            responsableProgramaService.eliminarResponsable(idUsuario, idPrograma);
            return ResponseEntity.ok("Usuario ID " + idUsuario
                    + " eliminado como responsable del programa ID " + idPrograma);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todos los programas de un usuario
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> eliminarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            responsableProgramaService.eliminarPorUsuario(idUsuario);
            return ResponseEntity.ok("Todas las responsabilidades del usuario ID "
                    + idUsuario + " eliminadas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todos los responsables de un programa
    @DeleteMapping("/programa/{idPrograma}")
    public ResponseEntity<?> eliminarPorPrograma(@PathVariable Integer idPrograma) {
        try {
            responsableProgramaService.eliminarPorPrograma(idPrograma);
            return ResponseEntity.ok("Todos los responsables del programa ID "
                    + idPrograma + " eliminados correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
