package com.centrocomunitario.controller;

import com.centrocomunitario.model.ComentarioArchivo;
import com.centrocomunitario.service.ComentarioArchivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentario-archivos")
@CrossOrigin(origins = "*")
public class ComentarioArchivoController {

    @Autowired
    private ComentarioArchivoService comentarioArchivoService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ComentarioArchivo>> obtenerTodos() {
        return ResponseEntity.ok(comentarioArchivoService.obtenerTodos());
    }

    // Archivos de un comentario
    @GetMapping("/comentario/{idComentario}")
    public ResponseEntity<?> obtenerPorComentario(@PathVariable Integer idComentario) {
        try {
            return ResponseEntity.ok(comentarioArchivoService.obtenerPorComentario(idComentario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por clave compuesta
    @GetMapping("/{idComentario}/{archivoAdjunto}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idComentario,
                                          @PathVariable String archivoAdjunto) {
        try {
            return ResponseEntity.ok(comentarioArchivoService.obtenerPorId(idComentario, archivoAdjunto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable String tipo) {
        try {
            ComentarioArchivo.Tipo tipoEnum = ComentarioArchivo.Tipo.valueOf(tipo);
            return ResponseEntity.ok(comentarioArchivoService.obtenerPorTipo(tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo no valido. Use: jpg, png, pdf, word");
        }
    }

    // Archivos de comentario por tipo
    @GetMapping("/comentario/{idComentario}/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorComentarioYTipo(@PathVariable Integer idComentario,
                                                        @PathVariable String tipo) {
        try {
            ComentarioArchivo.Tipo tipoEnum = ComentarioArchivo.Tipo.valueOf(tipo);
            return ResponseEntity.ok(comentarioArchivoService.obtenerPorComentarioYTipo(idComentario, tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo no valido. Use: jpg, png, pdf, word");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<ComentarioArchivo>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(comentarioArchivoService.buscarPorNombre(nombre));
    }

    // Agregar archivo a comentario
    @PostMapping
    public ResponseEntity<?> agregarArchivo(@Valid @RequestBody ComentarioArchivo archivo) {
        try {
            ComentarioArchivo nuevo = comentarioArchivoService.agregarArchivo(archivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar archivo especifico
    @DeleteMapping("/{idComentario}/{archivoAdjunto}")
    public ResponseEntity<?> eliminarArchivo(@PathVariable Integer idComentario,
                                             @PathVariable String archivoAdjunto) {
        try {
            comentarioArchivoService.eliminarArchivo(idComentario, archivoAdjunto);
            return ResponseEntity.ok("Archivo '" + archivoAdjunto
                    + "' eliminado del comentario con ID: " + idComentario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todos de un comentario
    @DeleteMapping("/comentario/{idComentario}")
    public ResponseEntity<?> eliminarArchivosPorComentario(@PathVariable Integer idComentario) {
        try {
            comentarioArchivoService.eliminarArchivosPorComentario(idComentario);
            return ResponseEntity.ok("Todos los archivos del comentario con ID "
                    + idComentario + " eliminados correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
