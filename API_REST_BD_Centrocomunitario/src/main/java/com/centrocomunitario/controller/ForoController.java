package com.centrocomunitario.controller;

import com.centrocomunitario.model.Foro;
import com.centrocomunitario.service.ForoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/foros")
@CrossOrigin(origins = "*")
public class ForoController {

    @Autowired
    private ForoService foroService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Foro>> obtenerTodos() {
        return ResponseEntity.ok(foroService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(foroService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Foros de una actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(foroService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Foros de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(foroService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Filtrar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String estado) {
        try {
            Foro.Estado estadoEnum = Foro.Estado.valueOf(estado);
            return ResponseEntity.ok(foroService.obtenerPorEstado(estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: abierto, cerrado, oculto");
        }
    }

    // Foros de actividad por estado
    @GetMapping("/actividad/{idActividad}/estado/{estado}")
    public ResponseEntity<?> obtenerPorActividadYEstado(@PathVariable Integer idActividad,
                                                         @PathVariable String estado) {
        try {
            Foro.Estado estadoEnum = Foro.Estado.valueOf(estado);
            return ResponseEntity.ok(foroService.obtenerPorActividadYEstado(idActividad, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: abierto, cerrado, oculto");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por titulo
    @GetMapping("/buscar")
    public ResponseEntity<List<Foro>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(foroService.buscarPorTitulo(titulo));
    }

    // Filtrar por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDateTime inicio,
                                                    @RequestParam LocalDateTime fin) {
        try {
            return ResponseEntity.ok(foroService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Crear foro
    @PostMapping
    public ResponseEntity<?> crearForo(@Valid @RequestBody Foro foro) {
        try {
            Foro nuevo = foroService.crearForo(foro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar foro
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarForo(@PathVariable Integer id,
                                            @Valid @RequestBody Foro foro) {
        try {
            return ResponseEntity.ok(foroService.actualizarForo(id, foro));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Cambiar solo el estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id,
                                           @RequestParam String estado) {
        try {
            Foro.Estado estadoEnum = Foro.Estado.valueOf(estado);
            return ResponseEntity.ok(foroService.cambiarEstado(id, estadoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado no valido. Use: abierto, cerrado, oculto");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar foro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarForo(@PathVariable Integer id) {
        try {
            foroService.eliminarForo(id);
            return ResponseEntity.ok("Foro con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
