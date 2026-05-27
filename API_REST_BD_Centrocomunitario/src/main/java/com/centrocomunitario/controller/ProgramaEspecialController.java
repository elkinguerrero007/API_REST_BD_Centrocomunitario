package com.centrocomunitario.controller;

import com.centrocomunitario.model.ProgramaEspecial;
import com.centrocomunitario.service.ProgramaEspecialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/programas")
@CrossOrigin(origins = "*")
public class ProgramaEspecialController {

    @Autowired
    private ProgramaEspecialService programaEspecialService;

    //obtener todos
    @GetMapping
    public ResponseEntity<List<ProgramaEspecial>> obtenerTodos() {
        return ResponseEntity.ok(programaEspecialService.obtenerTodos());
    }

    //obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(programaEspecialService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //obtener por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(programaEspecialService.obtenerPorNombre(nombre));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //buscar por palabra clave
    @GetMapping("/buscar")
    public ResponseEntity<List<ProgramaEspecial>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(programaEspecialService.buscarPorNombre(nombre));
    }

    //obtener por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProgramaEspecial>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(programaEspecialService.obtenerPorTipo(tipo));
    }

    // Obtener por poblacion objetivo
    @GetMapping("/poblacion/{poblacion}")
    public ResponseEntity<List<ProgramaEspecial>> obtenerPorPoblacion(@PathVariable String poblacion) {
        return ResponseEntity.ok(programaEspecialService.obtenerPorPoblacion(poblacion));
    }

    //obtener programas activos
    @GetMapping("/activos")
    public ResponseEntity<List<ProgramaEspecial>> obtenerActivos() {
        return ResponseEntity.ok(programaEspecialService.obtenerProgramasActivos());
    }

    //obtener por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> obtenerPorRangoFechas(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fin) {
        try {
            return ResponseEntity.ok(programaEspecialService.obtenerPorRangoFechas(inicio, fin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //crear programa
    @PostMapping
    public ResponseEntity<?> crearPrograma(@Valid @RequestBody ProgramaEspecial programa) {
        try {
            ProgramaEspecial nuevo = programaEspecialService.crearPrograma(programa);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //actualizar programa
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPrograma(@PathVariable Integer id,
                                                @Valid @RequestBody ProgramaEspecial programa) {
        try {
            return ResponseEntity.ok(programaEspecialService.actualizarPrograma(id, programa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //eliminar programa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrograma(@PathVariable Integer id) {
        try {
            programaEspecialService.eliminarPrograma(id);
            return ResponseEntity.ok("Programa con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}