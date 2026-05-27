package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Responsableprograma")
@IdClass(ResponsableProgramaId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsablePrograma {

    @Id
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Id
    @NotNull(message = "El ID del programa es obligatorio")
    @Column(name = "idPrograma", nullable = false)
    private Integer idPrograma;
}
