package com.centrocomunitario.repository;

import com.centrocomunitario.model.ActividadRecurso;
import com.centrocomunitario.model.ActividadRecursoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRecursoRepository extends JpaRepository<ActividadRecurso, ActividadRecursoId> {

    List<ActividadRecurso> findByIdActividad(Integer idActividad);

    List<ActividadRecurso> findByRecursoRequeridoContainingIgnoreCase(String recurso);

    boolean existsByIdActividadAndRecursoRequerido(Integer idActividad, String recursoRequerido);

    void deleteByIdActividad(Integer idActividad);
}
