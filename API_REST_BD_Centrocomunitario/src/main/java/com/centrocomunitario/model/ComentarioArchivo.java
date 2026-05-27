package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Comentarioarchivo")
@IdClass(ComentarioArchivoId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioArchivo {

    public enum Tipo {
        jpg, png, pdf, word
    }

    @Id
    @NotNull(message = "El ID del comentario es obligatorio")
    @Column(name = "idComentario", nullable = false)
    private Integer idComentario;

    @Id
    @NotBlank(message = "El archivo adjunto es obligatorio")
    @Size(max = 255, message = "El nombre del archivo no puede superar los 255 caracteres")
    @Column(name = "archivoAdjunto", nullable = false, length = 255)
    private String archivoAdjunto;

    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El tipo de archivo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false,
            columnDefinition = "ENUM('jpg','png','pdf','word')")
    private Tipo tipo;

    @NotBlank(message = "La URL del archivo es obligatoria")
    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    @Column(name = "url", nullable = false, length = 500)
    private String url;
}
