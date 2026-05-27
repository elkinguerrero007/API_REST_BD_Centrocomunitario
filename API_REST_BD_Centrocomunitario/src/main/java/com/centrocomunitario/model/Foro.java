package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Foro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foro {

    public enum Estado {
        abierto, cerrado, oculto
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idForo")
    private Integer idForo;

    @NotBlank(message = "El titulo del foro es obligatorio")
    @Size(max = 150, message = "El titulo no puede superar los 150 caracteres")
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "El contenido del foro es obligatorio")
    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @NotNull(message = "La fecha de publicacion es obligatoria")
    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDateTime fechaPublicacion;

    @NotNull(message = "El estado del foro es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false,
            columnDefinition = "ENUM('abierto','cerrado','oculto')")
    private Estado estado;

    @NotNull(message = "El ID de la actividad es obligatorio")
    @Column(name = "idActividad", nullable = false)
    private Integer idActividad;

    @NotNull(message = "El ID del usuario creador es obligatorio")
    @Column(name = "idUsuarioCreador", nullable = false)
    private Integer idUsuarioCreador;
}
