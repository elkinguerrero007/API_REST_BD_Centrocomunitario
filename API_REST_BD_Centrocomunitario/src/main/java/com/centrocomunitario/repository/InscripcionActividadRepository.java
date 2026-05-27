package com.centrocomunitario.repository;

import com.centrocomunitario.model.InscripcionActividad;
import com.centrocomunitario.model.InscripcionActividadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InscripcionActividadRepository extends JpaRepository<InscripcionActividad, InscripcionActividadId> {

    List<InscripcionActividad> findByIdUsuario(Integer idUsuario);

    List<InscripcionActividad> findByIdActividad(Integer idActividad);

    List<InscripcionActividad> findByEstado(InscripcionActividad.Estado estado);

    List<InscripcionActividad> findByIdActividadAndEstado(Integer idActividad, InscripcionActividad.Estado estado);

    List<InscripcionActividad> findByIdUsuarioAndEstado(Integer idUsuario, InscripcionActividad.Estado estado);

    List<InscripcionActividad> findByFechaInscripcionBetween(LocalDate inicio, LocalDate fin);

    boolean existsByIdUsuarioAndIdActividad(Integer idUsuario, Integer idActividad);

    long countByIdActividad(Integer idActividad);

    long countByIdActividadAndEstado(Integer idActividad, InscripcionActividad.Estado estado);

    void deleteByIdUsuario(Integer idUsuario);

    void deleteByIdActividad(Integer idActividad);
}
