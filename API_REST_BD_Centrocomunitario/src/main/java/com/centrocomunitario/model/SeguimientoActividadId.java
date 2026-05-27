package com.centrocomunitario.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clave primaria compuesta de SeguimientoActividad.
 * PK: (idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro)
 */
public class SeguimientoActividadId implements Serializable {

    private Integer idUsuario;
    private Integer idInstructor;   // NUEVO campo en la PK
    private Integer idParticipante;
    private Integer idActividad;
    private LocalDateTime fechaRegistro;

    public SeguimientoActividadId() {}

    public SeguimientoActividadId(Integer idUsuario, Integer idInstructor,
                                   Integer idParticipante, Integer idActividad,
                                   LocalDateTime fechaRegistro) {
        this.idUsuario = idUsuario;
        this.idInstructor = idInstructor;
        this.idParticipante = idParticipante;
        this.idActividad = idActividad;
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeguimientoActividadId)) return false;
        SeguimientoActividadId that = (SeguimientoActividadId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idInstructor, that.idInstructor) &&
               Objects.equals(idParticipante, that.idParticipante) &&
               Objects.equals(idActividad, that.idActividad) &&
               Objects.equals(fechaRegistro, that.fechaRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idInstructor, idParticipante, idActividad, fechaRegistro);
    }
}
