package com.centrocomunitario.repository;

import com.centrocomunitario.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Integer> {

    List<Anuncio> findByIdUsuario(Integer idUsuario);

    List<Anuncio> findByIdActividad(Integer idActividad);

    List<Anuncio> findByIdActividadIsNull();

    List<Anuncio> findByTituloContainingIgnoreCase(String titulo);

    List<Anuncio> findByFecha(LocalDate fecha);

    List<Anuncio> findByFechaBetween(LocalDate inicio, LocalDate fin);

    List<Anuncio> findByFechaGreaterThanEqual(LocalDate fecha);
}
