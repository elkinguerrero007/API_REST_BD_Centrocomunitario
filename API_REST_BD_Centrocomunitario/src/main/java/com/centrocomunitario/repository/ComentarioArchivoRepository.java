package com.centrocomunitario.repository;

import com.centrocomunitario.model.ComentarioArchivo;
import com.centrocomunitario.model.ComentarioArchivoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioArchivoRepository extends JpaRepository<ComentarioArchivo, ComentarioArchivoId> {

    List<ComentarioArchivo> findByIdComentario(Integer idComentario);

    List<ComentarioArchivo> findByTipo(ComentarioArchivo.Tipo tipo);

    List<ComentarioArchivo> findByIdComentarioAndTipo(Integer idComentario, ComentarioArchivo.Tipo tipo);

    List<ComentarioArchivo> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByIdComentarioAndArchivoAdjunto(Integer idComentario, String archivoAdjunto);

    void deleteByIdComentario(Integer idComentario);
}
