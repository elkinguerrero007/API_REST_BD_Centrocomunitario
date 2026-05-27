package com.centrocomunitario.repository;

import com.centrocomunitario.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

    List<Actividad> findByNombreContainingIgnoreCase(String nombre);

    List<Actividad> findByEstado(Actividad.Estado estado);

    List<Actividad> findByIdCategoria(Integer idCategoria);

    List<Actividad> findByIdPrograma(Integer idPrograma);

    List<Actividad> findByIdInstructor(Integer idInstructor);

    List<Actividad> findByIdProponente(Integer idProponente);

    List<Actividad> findByIdAprobador(Integer idAprobador);

    List<Actividad> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);

    List<Actividad> findByFechaFinalizacionGreaterThanEqual(LocalDate fecha);

    boolean existsByNombre(String nombre);
}
