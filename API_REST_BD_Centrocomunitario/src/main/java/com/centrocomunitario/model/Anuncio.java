package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Anuncio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAnuncio")
    private Integer idAnuncio;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "idActividad")
    private Integer idActividad;

    @NotBlank(message = "El titulo del anuncio es obligatorio")
    @Size(max = 150, message = "El titulo no puede superar los 150 caracteres")
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "El contenido del anuncio es obligatorio")
    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @NotNull(message = "La fecha del anuncio es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
}
