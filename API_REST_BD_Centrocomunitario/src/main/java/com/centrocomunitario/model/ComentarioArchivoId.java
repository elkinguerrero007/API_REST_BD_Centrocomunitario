package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class ComentarioArchivoId implements Serializable {

    private Integer idComentario;
    private String archivoAdjunto;

    public ComentarioArchivoId() {}

    public ComentarioArchivoId(Integer idComentario, String archivoAdjunto) {
        this.idComentario = idComentario;
        this.archivoAdjunto = archivoAdjunto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComentarioArchivoId)) return false;
        ComentarioArchivoId that = (ComentarioArchivoId) o;
        return Objects.equals(idComentario, that.idComentario) &&
               Objects.equals(archivoAdjunto, that.archivoAdjunto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idComentario, archivoAdjunto);
    }
}
