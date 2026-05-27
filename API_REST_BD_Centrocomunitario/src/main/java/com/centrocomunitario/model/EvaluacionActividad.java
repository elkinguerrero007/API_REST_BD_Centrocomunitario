package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Evaluacionactividad")
@IdClass(EvaluacionActividadId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionActividad {

    @Id
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Id
    @NotNull(message = "El ID de la actividad es obligatorio")
    @Column(name = "idActividad", nullable = false)
    private Integer idActividad;

    @NotNull(message = "La fecha de evaluacion es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @NotNull(message = "La valoracion es obligatoria")
    @Min(value = 1, message = "La valoracion minima es 1")
    @Max(value = 5, message = "La valoracion maxima es 5")
    @Column(name = "valoracion", nullable = false)
    private Integer valoracion;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "sugerencias_mejora", columnDefinition = "TEXT")
    private String sugerenciasMejora;
}
