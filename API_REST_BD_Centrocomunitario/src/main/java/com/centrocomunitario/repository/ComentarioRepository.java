package com.centrocomunitario.repository;

import com.centrocomunitario.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findByIdForo(Integer idForo);

    List<Comentario> findByIdUsuario(Integer idUsuario);

    List<Comentario> findByIdComentarioPadre(Integer idComentarioPadre);

    List<Comentario> findByIdForoAndIdComentarioPadreIsNull(Integer idForo);

    List<Comentario> findByIdForoAndIdUsuario(Integer idForo, Integer idUsuario);

    List<Comentario> findByFechaPublicacionBetween(LocalDate inicio, LocalDate fin);

    long countByIdForo(Integer idForo);

    long countByIdComentarioPadre(Integer idComentarioPadre);
}
