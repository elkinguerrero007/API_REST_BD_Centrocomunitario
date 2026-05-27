package com.centrocomunitario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "Asistenciasesion")
@IdClass(AsistenciaSesionId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaSesion {

    public enum Estado {
        asistio("asistio"),
        no_asistio("no asistio");

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
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Id
    @NotNull(message = "El ID de la sesion es obligatorio")
    @Column(name = "idSesion", nullable = false)
    private Integer idSesion;

    @Column(name = "horaRegistro")
    private LocalTime horaRegistro;

    @NotNull(message = "El estado de asistencia es obligatorio")
    @Convert(converter = EstadoConverter.class)
    @Column(name = "estado", nullable = false,
            columnDefinition = "ENUM('asistio','no asistio')")
    private Estado estado;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}
