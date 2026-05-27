package com.centrocomunitario.service;

import com.centrocomunitario.model.Usuario;
import com.centrocomunitario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear usuario
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        if (usuarioRepository.existsByDocumentoIdentificacion(usuario.getDocumentoIdentificacion())) {
            throw new RuntimeException("Ya existe un usuario con el documento: " + usuario.getDocumentoIdentificacion());
        }
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios 
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar por ID 
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // Buscar por email
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    // Buscar por documento
    public Usuario obtenerPorDocumento(String documento) {
        return usuarioRepository.findByDocumentoIdentificacion(documento)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con documento: " + documento));
    }

    // Buscar por rol 
    public List<Usuario> obtenerPorRol(Usuario.Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    // Actualizar usuario
    public Usuario actualizarUsuario(Integer id, Usuario datosNuevos) {
        Usuario usuarioExistente = obtenerPorId(id);

        if (!usuarioExistente.getEmail().equals(datosNuevos.getEmail()) &&
                usuarioRepository.existsByEmail(datosNuevos.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + datosNuevos.getEmail());
        }

        if (!usuarioExistente.getDocumentoIdentificacion().equals(datosNuevos.getDocumentoIdentificacion()) &&
                usuarioRepository.existsByDocumentoIdentificacion(datosNuevos.getDocumentoIdentificacion())) {
            throw new RuntimeException("Ya existe un usuario con el documento: " + datosNuevos.getDocumentoIdentificacion());
        }

        usuarioExistente.setNombreCompleto(datosNuevos.getNombreCompleto());
        usuarioExistente.setDocumentoIdentificacion(datosNuevos.getDocumentoIdentificacion());
        usuarioExistente.setEdad(datosNuevos.getEdad());
        usuarioExistente.setEmail(datosNuevos.getEmail());
        usuarioExistente.setTelefono(datosNuevos.getTelefono());
        usuarioExistente.setDireccionResidencia(datosNuevos.getDireccionResidencia());
        usuarioExistente.setRol(datosNuevos.getRol());

        return usuarioRepository.save(usuarioExistente);
    }

    // Eliminar usuario 
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
