package com.centrocomunitario.repository;

import com.centrocomunitario.model.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Integer> {

    Optional<Espacio> findByNombre(String nombre);

    List<Espacio> findByNombreContainingIgnoreCase(String nombre);

    List<Espacio> findByEstado(Espacio.Estado estado);

    List<Espacio> findByCapacidadGreaterThanEqual(Integer capacidad);

    List<Espacio> findByUbicacionContainingIgnoreCase(String ubicacion);

    boolean existsByNombre(String nombre);
}
