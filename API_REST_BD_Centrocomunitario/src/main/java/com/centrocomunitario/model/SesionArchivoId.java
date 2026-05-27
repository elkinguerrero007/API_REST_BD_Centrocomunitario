package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class SesionArchivoId implements Serializable {

    private String archivoAdjunto;
    private Integer idSesion;

    public SesionArchivoId() {}

    public SesionArchivoId(String archivoAdjunto, Integer idSesion) {
        this.archivoAdjunto = archivoAdjunto;
        this.idSesion = idSesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SesionArchivoId)) return false;
        SesionArchivoId that = (SesionArchivoId) o;
        return Objects.equals(archivoAdjunto, that.archivoAdjunto) &&
               Objects.equals(idSesion, that.idSesion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archivoAdjunto, idSesion);
    }
}
