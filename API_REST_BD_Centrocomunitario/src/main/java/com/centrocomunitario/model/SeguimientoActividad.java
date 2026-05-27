package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad SeguimientoActividad.
 * Tabla: Seguimientoactividad
 * PK compuesta: (idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro)
 */
@Entity
@Table(name = "Seguimientoactividad")
@IdClass(SeguimientoActividadId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoActividad {

    // ── Clave primaria compuesta ─────────────────────────────────────

    @Id
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Id
    @NotNull(message = "El ID del instructor es obligatorio")
    @Column(name = "idInstructor", nullable = false)
    private Integer idInstructor;

    @Id
    @NotNull(message = "El ID del participante es obligatorio")
    @Column(name = "idParticipante", nullable = false)
    private Integer idParticipante;

    @Id
    @NotNull(message = "El ID de la actividad es obligatorio")
    @Column(name = "idActividad", nullable = false)
    private Integer idActividad;

    @Id
    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    // ── Campos no clave ──────────────────────────────────────────────

    @NotBlank(message = "El comentario es obligatorio")
    @Column(name = "comentario", nullable = false, columnDefinition = "TEXT")
    private String comentario;

    @NotBlank(message = "El aspecto evaluado es obligatorio")
    @Size(max = 100, message = "El aspecto evaluado no puede superar los 100 caracteres")
    @Column(name = "aspecto_evaluado", nullable = false, length = 100)
    private String aspectoEvaluado;

    @NotBlank(message = "El nivel de progreso es obligatorio")
    @Size(max = 50, message = "El nivel de progreso no puede superar los 50 caracteres")
    @Column(name = "nivelProgreso", nullable = false, length = 50)
    private String nivelProgreso;

    @NotBlank(message = "Las observaciones son obligatorias")
    @Column(name = "observaciones", nullable = false, columnDefinition = "TEXT")
    private String observaciones;
}
