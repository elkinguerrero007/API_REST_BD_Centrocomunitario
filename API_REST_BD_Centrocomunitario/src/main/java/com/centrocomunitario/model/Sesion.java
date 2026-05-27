package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Sesion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sesion {

    public enum Modalidad {
        presencial, virtual, hibrida
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSesion")
    private Integer idSesion;

    @NotNull(message = "El ID de la actividad es obligatorio")
    @Column(name = "idActividad", nullable = false)
    private Integer idActividad;

    @NotNull(message = "El ID del espacio es obligatorio")
    @Column(name = "idEspacio", nullable = false)
    private Integer idEspacio;

    @NotNull(message = "La fecha de la sesion es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de finalizacion es obligatoria")
    @Column(name = "hora_finalizacion", nullable = false)
    private LocalTime horaFinalizacion;

    @NotNull(message = "La modalidad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad", nullable = false,
            columnDefinition = "ENUM('presencial','virtual','hibrida')")
    private Modalidad modalidad;

    @Column(name = "enlace_acceso", length = 255)
    private String enlaceAcceso;

    @NotBlank(message = "El tema de la sesion es obligatorio")
    @Size(max = 150, message = "El tema no puede superar los 150 caracteres")
    @Column(name = "tema", nullable = false, length = 150)
    private String tema;
}
