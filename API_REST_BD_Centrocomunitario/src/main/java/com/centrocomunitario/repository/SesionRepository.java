package com.centrocomunitario.repository;

import com.centrocomunitario.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {

    List<Sesion> findByIdActividad(Integer idActividad);

    List<Sesion> findByIdEspacio(Integer idEspacio);

    List<Sesion> findByFecha(LocalDate fecha);

    List<Sesion> findByFechaBetween(LocalDate inicio, LocalDate fin);

    List<Sesion> findByModalidad(Sesion.Modalidad modalidad);

    List<Sesion> findByIdActividadAndFecha(Integer idActividad, LocalDate fecha);

    List<Sesion> findByIdEspacioAndFecha(Integer idEspacio, LocalDate fecha);

    List<Sesion> findByTemaContainingIgnoreCase(String tema);
}
