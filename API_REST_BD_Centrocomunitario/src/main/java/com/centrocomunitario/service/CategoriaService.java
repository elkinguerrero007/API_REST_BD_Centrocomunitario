package com.centrocomunitario.service;

import com.centrocomunitario.model.Categoria;
import com.centrocomunitario.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Crear categoria
    public Categoria crearCategoria(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoria con el nombre: " + categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }

    // Obtener todas las categorias
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    // Buscar por ID
    public Categoria obtenerPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
    }

    // Buscar por nombre exacto
    public Categoria obtenerPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con nombre: " + nombre));
    }

    // Buscar por palabra clave en el nombre
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Actualizar categoria
    public Categoria actualizarCategoria(Integer id, Categoria datosNuevos) {
        Categoria categoriaExistente = obtenerPorId(id);

        if (!categoriaExistente.getNombre().equals(datosNuevos.getNombre()) &&
                categoriaRepository.existsByNombre(datosNuevos.getNombre())) {
            throw new RuntimeException("Ya existe una categoria con el nombre: " + datosNuevos.getNombre());
        }

        categoriaExistente.setNombre(datosNuevos.getNombre());
        categoriaExistente.setDescripcion(datosNuevos.getDescripcion());

        return categoriaRepository.save(categoriaExistente);
    }

    // Eliminar categoria
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Categoria no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
