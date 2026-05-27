package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class AsistenciaSesionId implements Serializable {

    private Integer idUsuario;
    private Integer idSesion;

    public AsistenciaSesionId() {}

    public AsistenciaSesionId(Integer idUsuario, Integer idSesion) {
        this.idUsuario = idUsuario;
        this.idSesion = idSesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsistenciaSesionId)) return false;
        AsistenciaSesionId that = (AsistenciaSesionId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idSesion, that.idSesion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idSesion);
    }
}
