package com.centrocomunitario.repository;

import com.centrocomunitario.model.Foro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ForoRepository extends JpaRepository<Foro, Integer> {

    List<Foro> findByIdActividad(Integer idActividad);

    List<Foro> findByIdUsuarioCreador(Integer idUsuarioCreador);

    List<Foro> findByEstado(Foro.Estado estado);

    List<Foro> findByIdActividadAndEstado(Integer idActividad, Foro.Estado estado);

    List<Foro> findByTituloContainingIgnoreCase(String titulo);

    List<Foro> findByFechaPublicacionBetween(LocalDateTime inicio, LocalDateTime fin);

    boolean existsByTituloAndIdActividad(String titulo, Integer idActividad);
}
