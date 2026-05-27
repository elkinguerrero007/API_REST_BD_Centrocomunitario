package com.centrocomunitario.repository;

import com.centrocomunitario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDocumentoIdentificacion(String documentoIdentificacion);

    Optional<Usuario> findByTelefono(String telefono);

    List<Usuario> findByRol(Usuario.Rol rol);

    boolean existsByEmail(String email);

    boolean existsByDocumentoIdentificacion(String documentoIdentificacion);
}
