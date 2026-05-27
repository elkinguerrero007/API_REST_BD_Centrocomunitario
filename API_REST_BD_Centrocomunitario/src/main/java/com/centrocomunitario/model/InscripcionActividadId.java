package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class InscripcionActividadId implements Serializable {

    private Integer idUsuario;
    private Integer idActividad;

    public InscripcionActividadId() {}

    public InscripcionActividadId(Integer idUsuario, Integer idActividad) {
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InscripcionActividadId)) return false;
        InscripcionActividadId that = (InscripcionActividadId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idActividad, that.idActividad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idActividad);
    }
}
