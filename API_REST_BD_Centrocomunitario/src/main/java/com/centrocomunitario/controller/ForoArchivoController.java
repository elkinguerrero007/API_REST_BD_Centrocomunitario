package com.centrocomunitario.controller;

import com.centrocomunitario.model.ForoArchivo;
import com.centrocomunitario.service.ForoArchivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foro-archivos")
@CrossOrigin(origins = "*")
public class ForoArchivoController {

    @Autowired
    private ForoArchivoService foroArchivoService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ForoArchivo>> obtenerTodos() {
        return ResponseEntity.ok(foroArchivoService.obtenerTodos());
    }

    // Archivos de un foro
    @GetMapping("/foro/{idForo}")
    public ResponseEntity<?> obtenerPorForo(@PathVariable Integer idForo) {
        try {
            return ResponseEntity.ok(foroArchivoService.obtenerPorForo(idForo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por clave compuesta
    @GetMapping("/{archivoAdjunto}/{idForo}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String archivoAdjunto,
                                          @PathVariable Integer idForo) {
        try {
            return ResponseEntity.ok(foroArchivoService.obtenerPorId(archivoAdjunto, idForo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Obtener por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable String tipo) {
        try {
            ForoArchivo.Tipo tipoEnum = ForoArchivo.Tipo.valueOf(tipo);
            return ResponseEntity.ok(foroArchivoService.obtenerPorTipo(tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo no valido. Use: jpg, png, pdf, word");
        }
    }

    // Archivos de foro por tipo
    @GetMapping("/foro/{idForo}/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorForoYTipo(@PathVariable Integer idForo,
                                                  @PathVariable String tipo) {
        try {
            ForoArchivo.Tipo tipoEnum = ForoArchivo.Tipo.valueOf(tipo);
            return ResponseEntity.ok(foroArchivoService.obtenerPorForoYTipo(idForo, tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo no valido. Use: jpg, png, pdf, word");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<ForoArchivo>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(foroArchivoService.buscarPorNombre(nombre));
    }

    // Agregar archivo a foro
    @PostMapping
    public ResponseEntity<?> agregarArchivo(@Valid @RequestBody ForoArchivo archivo) {
        try {
            ForoArchivo nuevo = foroArchivoService.agregarArchivo(archivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar archivo especifico
    @DeleteMapping("/{archivoAdjunto}/{idForo}")
    public ResponseEntity<?> eliminarArchivo(@PathVariable String archivoAdjunto,
                                             @PathVariable Integer idForo) {
        try {
            foroArchivoService.eliminarArchivo(archivoAdjunto, idForo);
            return ResponseEntity.ok("Archivo '" + archivoAdjunto
                    + "' eliminado del foro con ID: " + idForo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar todos los archivos de un foro
    @DeleteMapping("/foro/{idForo}")
    public ResponseEntity<?> eliminarArchivosPorForo(@PathVariable Integer idForo) {
        try {
            foroArchivoService.eliminarArchivosPorForo(idForo);
            return ResponseEntity.ok("Todos los archivos del foro con ID "
                    + idForo + " eliminados correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
