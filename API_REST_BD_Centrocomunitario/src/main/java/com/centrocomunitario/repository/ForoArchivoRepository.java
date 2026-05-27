package com.centrocomunitario.repository;

import com.centrocomunitario.model.ForoArchivo;
import com.centrocomunitario.model.ForoArchivoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForoArchivoRepository extends JpaRepository<ForoArchivo, ForoArchivoId> {

    List<ForoArchivo> findByIdForo(Integer idForo);

    List<ForoArchivo> findByTipo(ForoArchivo.Tipo tipo);

    List<ForoArchivo> findByIdForoAndTipo(Integer idForo, ForoArchivo.Tipo tipo);

    List<ForoArchivo> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByArchivoAdjuntoAndIdForo(String archivoAdjunto, Integer idForo);

    void deleteByIdForo(Integer idForo);
}
