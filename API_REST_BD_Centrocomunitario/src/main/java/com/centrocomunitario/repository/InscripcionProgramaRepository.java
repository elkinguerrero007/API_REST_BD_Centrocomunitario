package com.centrocomunitario.repository;

import com.centrocomunitario.model.InscripcionPrograma;
import com.centrocomunitario.model.InscripcionProgramaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InscripcionProgramaRepository extends JpaRepository<InscripcionPrograma, InscripcionProgramaId> {

    List<InscripcionPrograma> findByIdUsuario(Integer idUsuario);

    List<InscripcionPrograma> findByIdPrograma(Integer idPrograma);

    List<InscripcionPrograma> findByFechaInscripcionBetween(LocalDate inicio, LocalDate fin);

    List<InscripcionPrograma> findByFechaInscripcionIsNull();

    boolean existsByIdUsuarioAndIdPrograma(Integer idUsuario, Integer idPrograma);

    long countByIdPrograma(Integer idPrograma);

    void deleteByIdUsuario(Integer idUsuario);

    void deleteByIdPrograma(Integer idPrograma);
}
