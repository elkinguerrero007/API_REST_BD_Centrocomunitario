package com.centrocomunitario.repository;

import com.centrocomunitario.model.EvaluacionActividad;
import com.centrocomunitario.model.EvaluacionActividadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvaluacionActividadRepository extends JpaRepository<EvaluacionActividad, EvaluacionActividadId> {

    List<EvaluacionActividad> findByIdActividad(Integer idActividad);

    List<EvaluacionActividad> findByIdUsuario(Integer idUsuario);

    List<EvaluacionActividad> findByValoracion(Integer valoracion);

    List<EvaluacionActividad> findByIdActividadAndValoracion(Integer idActividad, Integer valoracion);

    List<EvaluacionActividad> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    boolean existsByIdUsuarioAndIdActividad(Integer idUsuario, Integer idActividad);

    @Query("SELECT AVG(e.valoracion) FROM EvaluacionActividad e WHERE e.idActividad = :idActividad")
    Double promedioValoracionPorActividad(@Param("idActividad") Integer idActividad);

    void deleteByIdActividad(Integer idActividad);

    void deleteByIdUsuario(Integer idUsuario);
}
