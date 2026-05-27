package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Comentario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario")
    private Integer idComentario;

    @NotBlank(message = "El contenido del comentario es obligatorio")
    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @NotNull(message = "La fecha de publicacion es obligatoria")
    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @NotNull(message = "El ID del foro es obligatorio")
    @Column(name = "idForo", nullable = false)
    private Integer idForo;

    // FK auto-referenciada: null = comentario raiz, not null = respuesta a otro comentario
    @Column(name = "idComentarioPadre")
    private Integer idComentarioPadre;
}
