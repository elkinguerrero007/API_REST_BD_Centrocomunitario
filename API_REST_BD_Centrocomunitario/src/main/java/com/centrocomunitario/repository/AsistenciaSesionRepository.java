package com.centrocomunitario.repository;

import com.centrocomunitario.model.AsistenciaSesion;
import com.centrocomunitario.model.AsistenciaSesionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaSesionRepository extends JpaRepository<AsistenciaSesion, AsistenciaSesionId> {

    List<AsistenciaSesion> findByIdSesion(Integer idSesion);

    List<AsistenciaSesion> findByIdUsuario(Integer idUsuario);

    @Query(value = "SELECT * FROM Asistenciasesion WHERE estado = :estado", nativeQuery = true)
    List<AsistenciaSesion> findByEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM Asistenciasesion WHERE idSesion = :idSesion AND estado = :estado", nativeQuery = true)
    List<AsistenciaSesion> findByIdSesionAndEstado(@Param("idSesion") Integer idSesion,
                                                    @Param("estado") String estado);

    boolean existsByIdUsuarioAndIdSesion(Integer idUsuario, Integer idSesion);

    long countByIdSesionAndEstado(Integer idSesion, AsistenciaSesion.Estado estado);

    void deleteByIdSesion(Integer idSesion);

    void deleteByIdUsuario(Integer idUsuario);
}
