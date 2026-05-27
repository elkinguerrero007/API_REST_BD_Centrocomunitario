package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Programaespecial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrograma")
    private Integer idPrograma;

    @NotBlank(message = "El nombre del programa es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "La descripcion del programa es obligatoria")
    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotBlank(message = "La poblacion objetivo es obligatoria")
    @Size(max = 150, message = "La poblacion objetivo no puede superar los 150 caracteres")
    @Column(name = "poblacion_objetivo", nullable = false, length = 150)
    private String poblacionObjetivo;

    @NotBlank(message = "El tipo de programa es obligatorio")
    @Size(max = 80, message = "El tipo no puede superar los 80 caracteres")
    @Column(name = "tipo", nullable = false, length = 80)
    private String tipo;
}
