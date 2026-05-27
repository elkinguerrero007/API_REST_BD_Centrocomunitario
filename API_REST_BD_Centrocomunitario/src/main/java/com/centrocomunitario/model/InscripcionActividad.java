package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Inscripcionactividad")
@IdClass(InscripcionActividadId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionActividad {

    public enum Estado {
        inscrito, matriculado, retirado
    }

    @Id
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Id
    @NotNull(message = "El ID de la actividad es obligatorio")
    @Column(name = "idActividad", nullable = false)
    private Integer idActividad;

    @NotNull(message = "La fecha de inscripcion es obligatoria")
    @Column(name = "fechaInscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @NotNull(message = "El estado de inscripcion es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false,
            columnDefinition = "ENUM('inscrito','matriculado','retirado')")
    private Estado estado;
}
