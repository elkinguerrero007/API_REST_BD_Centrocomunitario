package com.centrocomunitario.controller;

import com.centrocomunitario.model.Comentario;
import com.centrocomunitario.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Comentario>> obtenerTodos() {
        return ResponseEntity.ok(comentarioService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Todos los comentarios de un foro
    @GetMapping("/foro/{idForo}")
    public ResponseEntity<?> obtenerPorForo(@PathVariable Integer idForo) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerPorForo(idForo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Comentarios raiz del foro
    @GetMapping("/foro/{idForo}/raices")
    public ResponseEntity<?> obtenerRaicesPorForo(@PathVariable Integer idForo) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerRaicesPorForo(idForo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Respuestas de un comentario
    @GetMapping("/{id}/respuestas")
    public ResponseEntity<?> obtenerRespuestas(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerRespuestas(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Contar respuestas
    @GetMapping("/{id}/respuestas/conteo")
    public ResponseEntity<?> contarRespuestas(@PathVariable Integer id) {
        try {
            long total = comentarioService.contarRespuestas(id);
            return ResponseEntity.ok("Respuestas del comentario ID " + id + ": " + total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Comentarios de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Comentarios de foro por usuario
    @GetMapping("/foro/{idForo}/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorForoYUsuario(@PathVariable Integer idForo,
                                                     @PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerPorForoYUsuario(idForo, idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Contar comentarios de un foro
    @GetMapping("/foro/{idForo}/conteo")
    public ResponseEntity<?> contarPorForo(@PathVariable Integer idForo) {
        try {
            long total = comentarioService.contarPorForo(idForo);
            return ResponseEntity.ok("Total de comentarios en el foro ID " + idForo + ": " + total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(comentarioService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Crear comentario
    @PostMapping
    public ResponseEntity<?> crearComentario(@Valid @RequestBody Comentario comentario) {
        try {
            Comentario nuevo = comentarioService.crearComentario(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar comentario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarComentario(@PathVariable Integer id,
                                                   @Valid @RequestBody Comentario comentario) {
        try {
            return ResponseEntity.ok(comentarioService.actualizarComentario(id, comentario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Integer id) {
        try {
            comentarioService.eliminarComentario(id);
            return ResponseEntity.ok("Comentario con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
