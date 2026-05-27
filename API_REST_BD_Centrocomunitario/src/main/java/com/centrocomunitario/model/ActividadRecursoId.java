package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class ActividadRecursoId implements Serializable {

    private Integer idActividad;
    private String recursoRequerido;

    public ActividadRecursoId() {}

    public ActividadRecursoId(Integer idActividad, String recursoRequerido) {
        this.idActividad = idActividad;
        this.recursoRequerido = recursoRequerido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActividadRecursoId)) return false;
        ActividadRecursoId that = (ActividadRecursoId) o;
        return Objects.equals(idActividad, that.idActividad) &&
               Objects.equals(recursoRequerido, that.recursoRequerido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActividad, recursoRequerido);
    }
}
