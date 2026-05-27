package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    public enum Rol {
        participante,
        instructor,
        coordinador,
        administrador,
        personal_centro,
        lider_comunitario
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @NotBlank(message = "El documento de identificacion es obligatorio")
    @Column(name = "documento_identificacion", nullable = false, unique = true, length = 30)
    private String documentoIdentificacion;

    @NotNull(message = "La edad es obligatoria")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Column(name = "telefono", nullable = false, unique = true, length = 30)
    private String telefono;

    @NotBlank(message = "La direccion de residencia es obligatoria")
    @Column(name = "direccion_residencia", nullable = false, length = 200)
    private String direccionResidencia;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(
        name = "rol",
        nullable = false,
        columnDefinition = "ENUM('participante','instructor','coordinador','administrador','personal_centro','lider_comunitario')"
    )
    private Rol rol;
}