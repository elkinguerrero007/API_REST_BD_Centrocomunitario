package com.centrocomunitario.controller;

import com.centrocomunitario.model.SesionArchivo;
import com.centrocomunitario.service.SesionArchivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesion-archivos")
@CrossOrigin(origins = "*")
public class SesionArchivoController {

    @Autowired
    private SesionArchivoService sesionArchivoService;

    //obtener todos
    @GetMapping
    public ResponseEntity<List<SesionArchivo>> obtenerTodos() {
        return ResponseEntity.ok(sesionArchivoService.obtenerTodos());
    }

    //archivos de una sesion
    @GetMapping("/sesion/{idSesion}")
    public ResponseEntity<?> obtenerPorSesion(@PathVariable Integer idSesion) {
        try {
            return ResponseEntity.ok(sesionArchivoService.obtenerPorSesion(idSesion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // obtener por clave compuesta
    @GetMapping("/{archivoAdjunto}/{idSesion}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String archivoAdjunto,
                                          @PathVariable Integer idSesion) {
        try {
            return ResponseEntity.ok(sesionArchivoService.obtenerPorId(archivoAdjunto, idSesion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //obtener por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable String tipo) {
        try {
            SesionArchivo.Tipo tipoEnum = SesionArchivo.Tipo.valueOf(tipo);
            return ResponseEntity.ok(sesionArchivoService.obtenerPorTipo(tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo no valido. Use: jpg, png, pdf, word");
        }
    }

    // archivos de sesion por tipo
    @GetMapping("/sesion/{idSesion}/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorSesionYTipo(@PathVariable Integer idSesion,
                                                    @PathVariable String tipo) {
        try {
            SesionArchivo.Tipo tipoEnum = SesionArchivo.Tipo.valueOf(tipo);
            return ResponseEntity.ok(sesionArchivoService.obtenerPorSesionYTipo(idSesion, tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo no valido. Use: jpg, png, pdf, word");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<SesionArchivo>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(sesionArchivoService.buscarPorNombre(nombre));
    }

    //agregar archivo a sesion
    @PostMapping
    public ResponseEntity<?> agregarArchivo(@Valid @RequestBody SesionArchivo archivo) {
        try {
            SesionArchivo nuevo = sesionArchivoService.agregarArchivo(archivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // eliminar archivo especifico
    @DeleteMapping("/{archivoAdjunto}/{idSesion}")
    public ResponseEntity<?> eliminarArchivo(@PathVariable String archivoAdjunto,
                                             @PathVariable Integer idSesion) {
        try {
            sesionArchivoService.eliminarArchivo(archivoAdjunto, idSesion);
            return ResponseEntity.ok("Archivo '" + archivoAdjunto
                    + "' eliminado de la sesion con ID: " + idSesion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // eliminar todos los archivos de una sesion
    @DeleteMapping("/sesion/{idSesion}")
    public ResponseEntity<?> eliminarArchivosPorSesion(@PathVariable Integer idSesion) {
        try {
            sesionArchivoService.eliminarArchivosPorSesion(idSesion);
            return ResponseEntity.ok("Todos los archivos de la sesion con ID "
                    + idSesion + " eliminados correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
