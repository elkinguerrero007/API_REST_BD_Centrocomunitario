package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Actividad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {

    public enum Estado {
        programada("programada"),
        en_curso("en curso"),
        finalizada("finalizada"),
        cancelada("cancelada");

        private final String valor;

        Estado(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }

        public static Estado fromValor(String valor) {
            for (Estado e : values()) {
                if (e.valor.equals(valor)) return e;
            }
            throw new IllegalArgumentException("Estado no valido: " + valor);
        }
    }

    @Converter
    public static class EstadoConverter implements AttributeConverter<Estado, String> {
        @Override
        public String convertToDatabaseColumn(Estado estado) {
            return estado == null ? null : estado.getValor();
        }

        @Override
        public Estado convertToEntityAttribute(String valor) {
            return valor == null ? null : Estado.fromValor(valor);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idActividad")
    private Integer idActividad;

    @NotBlank(message = "El nombre de la actividad es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "El objetivo es obligatorio")
    @Column(name = "objetivo", nullable = false, columnDefinition = "TEXT")
    private String objetivo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de finalizacion es obligatoria")
    @Column(name = "fecha_finalizacion", nullable = false)
    private LocalDate fechaFinalizacion;

    @NotNull(message = "La intensidad horaria es obligatoria")
    @Min(value = 1, message = "La intensidad horaria debe ser al menos 1 hora")
    @Column(name = "intensidad_horaria", nullable = false)
    private Integer intensidadHoraria;

    @NotNull(message = "El cupo maximo es obligatorio")
    @Min(value = 1, message = "El cupo maximo debe ser al menos 1")
    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    @NotNull(message = "El estado de la actividad es obligatorio")
    @Convert(converter = EstadoConverter.class)
    @Column(name = "estado", nullable = false,
            columnDefinition = "ENUM('programada','en curso','finalizada','cancelada')")
    private Estado estado;

    @NotNull(message = "La categoria es obligatoria")
    @Column(name = "idCategoria", nullable = false)
    private Integer idCategoria;

    @Column(name = "idPrograma")
    private Integer idPrograma;

    @Column(name = "idInstructor")
    private Integer idInstructor;

    @NotNull(message = "El proponente es obligatorio")
    @Column(name = "idProponente", nullable = false)
    private Integer idProponente;

    @NotNull(message = "El aprobador es obligatorio")
    @Column(name = "idAprobador", nullable = false)
    private Integer idAprobador;
}
