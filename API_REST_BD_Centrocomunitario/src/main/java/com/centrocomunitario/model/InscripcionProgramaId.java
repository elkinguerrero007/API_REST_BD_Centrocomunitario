package com.centrocomunitario.model;

import java.io.Serializable;
import java.util.Objects;

public class InscripcionProgramaId implements Serializable {

    private Integer idUsuario;
    private Integer idPrograma;

    public InscripcionProgramaId() {}

    public InscripcionProgramaId(Integer idUsuario, Integer idPrograma) {
        this.idUsuario = idUsuario;
        this.idPrograma = idPrograma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InscripcionProgramaId)) return false;
        InscripcionProgramaId that = (InscripcionProgramaId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idPrograma, that.idPrograma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idPrograma);
    }
}
