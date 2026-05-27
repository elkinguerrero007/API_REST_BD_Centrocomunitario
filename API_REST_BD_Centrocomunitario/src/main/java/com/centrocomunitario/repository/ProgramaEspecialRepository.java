package com.centrocomunitario.repository;

import com.centrocomunitario.model.ProgramaEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramaEspecialRepository extends JpaRepository<ProgramaEspecial, Integer> {

    Optional<ProgramaEspecial> findByNombre(String nombre);

    List<ProgramaEspecial> findByNombreContainingIgnoreCase(String nombre);

    List<ProgramaEspecial> findByTipo(String tipo);

    List<ProgramaEspecial> findByPoblacionObjetivo(String poblacionObjetivo);

    List<ProgramaEspecial> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);

    List<ProgramaEspecial> findByFechaFinGreaterThanEqual(LocalDate fecha);

    boolean existsByNombre(String nombre);
}
