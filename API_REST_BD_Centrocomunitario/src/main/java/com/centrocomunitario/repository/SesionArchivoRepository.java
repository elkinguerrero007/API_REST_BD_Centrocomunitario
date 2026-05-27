package com.centrocomunitario.repository;

import com.centrocomunitario.model.SesionArchivo;
import com.centrocomunitario.model.SesionArchivoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionArchivoRepository extends JpaRepository<SesionArchivo, SesionArchivoId> {

    List<SesionArchivo> findByIdSesion(Integer idSesion);

    List<SesionArchivo> findByTipo(SesionArchivo.Tipo tipo);

    List<SesionArchivo> findByIdSesionAndTipo(Integer idSesion, SesionArchivo.Tipo tipo);

    List<SesionArchivo> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByArchivoAdjuntoAndIdSesion(String archivoAdjunto, Integer idSesion);

    void deleteByIdSesion(Integer idSesion);
}
