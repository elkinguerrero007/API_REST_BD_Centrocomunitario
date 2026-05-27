package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Actividadrecurso")
@IdClass(ActividadRecursoId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadRecurso {

    @Id
    @NotNull(message = "El ID de la actividad es obligatorio")
    @Column(name = "idActividad", nullable = false)
    private Integer idActividad;

    @Id
    @NotBlank(message = "El recurso requerido es obligatorio")
    @Column(name = "recursoRequerido", nullable = false, length = 255)
    private String recursoRequerido;
}
