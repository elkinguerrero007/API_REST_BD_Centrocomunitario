package com.centrocomunitario.repository;

import com.centrocomunitario.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByNombre(String nombre);

    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombre(String nombre);
}
