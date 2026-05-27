package com.centrocomunitario.controller;

import com.centrocomunitario.model.Anuncio;
import com.centrocomunitario.service.AnuncioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
@CrossOrigin(origins = "*")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Anuncio>> obtenerTodos() {
        return ResponseEntity.ok(anuncioService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(anuncioService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Anuncios de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(anuncioService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Anuncios de una actividad
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> obtenerPorActividad(@PathVariable Integer idActividad) {
        try {
            return ResponseEntity.ok(anuncioService.obtenerPorActividad(idActividad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Anuncios sin actividad asociada
    @GetMapping("/generales")
    public ResponseEntity<List<Anuncio>> obtenerGenerales() {
        return ResponseEntity.ok(anuncioService.obtenerGenerales());
    }

    // Anuncios con fecha >= hoy
    @GetMapping("/vigentes")
    public ResponseEntity<List<Anuncio>> obtenerVigentes() {
        return ResponseEntity.ok(anuncioService.obtenerVigentes());
    }

    // Buscar por titulo
    @GetMapping("/buscar")
    public ResponseEntity<List<Anuncio>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(anuncioService.buscarPorTitulo(titulo));
    }

    // Anuncios en una fecha exacta
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Anuncio>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(anuncioService.obtenerPorFecha(fecha));
    }

    // Anuncios en un rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(anuncioService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Crear anuncio
    @PostMapping
    public ResponseEntity<?> crearAnuncio(@Valid @RequestBody Anuncio anuncio) {
        try {
            Anuncio nuevo = anuncioService.crearAnuncio(anuncio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar anuncio
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAnuncio(@PathVariable Integer id,
                                               @Valid @RequestBody Anuncio anuncio) {
        try {
            return ResponseEntity.ok(anuncioService.actualizarAnuncio(id, anuncio));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar anuncio
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAnuncio(@PathVariable Integer id) {
        try {
            anuncioService.eliminarAnuncio(id);
            return ResponseEntity.ok("Anuncio con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
