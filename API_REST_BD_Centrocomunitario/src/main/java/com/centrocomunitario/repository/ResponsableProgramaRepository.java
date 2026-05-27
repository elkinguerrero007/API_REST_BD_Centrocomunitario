package com.centrocomunitario.repository;

import com.centrocomunitario.model.ResponsablePrograma;
import com.centrocomunitario.model.ResponsableProgramaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsableProgramaRepository extends JpaRepository<ResponsablePrograma, ResponsableProgramaId> {

    List<ResponsablePrograma> findByIdUsuario(Integer idUsuario);

    List<ResponsablePrograma> findByIdPrograma(Integer idPrograma);

    boolean existsByIdUsuarioAndIdPrograma(Integer idUsuario, Integer idPrograma);

    long countByIdPrograma(Integer idPrograma);

    void deleteByIdUsuario(Integer idUsuario);

    void deleteByIdPrograma(Integer idPrograma);
}
