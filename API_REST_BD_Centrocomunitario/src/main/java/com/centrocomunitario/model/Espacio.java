package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Espacio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Espacio {

    public enum Estado {
        habilitado, inhabilitado
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspacio")
    private Integer idEspacio;

    @NotBlank(message = "El nombre del espacio es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La ubicacion del espacio es obligatoria")
    @Size(max = 150, message = "La ubicacion no puede superar los 150 caracteres")
    @Column(name = "ubicacion", nullable = false, length = 150)
    private String ubicacion;

    @NotNull(message = "La capacidad del espacio es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El estado del espacio es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, columnDefinition = "ENUM('habilitado','inhabilitado')")
    private Estado estado;
}
