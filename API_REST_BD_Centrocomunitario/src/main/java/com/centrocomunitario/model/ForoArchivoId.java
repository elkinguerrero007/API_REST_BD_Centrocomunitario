package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class ForoArchivoId implements Serializable {

    private String archivoAdjunto;
    private Integer idForo;

    public ForoArchivoId() {}

    public ForoArchivoId(String archivoAdjunto, Integer idForo) {
        this.archivoAdjunto = archivoAdjunto;
        this.idForo = idForo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForoArchivoId)) return false;
        ForoArchivoId that = (ForoArchivoId) o;
        return Objects.equals(archivoAdjunto, that.archivoAdjunto) &&
               Objects.equals(idForo, that.idForo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archivoAdjunto, idForo);
    }
}
