package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Foroarchivo")
@IdClass(ForoArchivoId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForoArchivo {

    public enum Tipo {
        jpg, png, pdf, word
    }

    @Id
    @NotBlank(message = "El archivo adjunto es obligatorio")
    @Size(max = 255, message = "El nombre del archivo no puede superar los 255 caracteres")
    @Column(name = "archivoAdjunto", nullable = false, length = 255)
    private String archivoAdjunto;

    @Id
    @NotNull(message = "El ID del foro es obligatorio")
    @Column(name = "idForo", nullable = false)
    private Integer idForo;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @NotBlank(message = "La descripcion del archivo es obligatoria")
    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El tipo de archivo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false,
            columnDefinition = "ENUM('jpg','png','pdf','word')")
    private Tipo tipo;

    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    @Column(name = "url", length = 500)
    private String url;
}
