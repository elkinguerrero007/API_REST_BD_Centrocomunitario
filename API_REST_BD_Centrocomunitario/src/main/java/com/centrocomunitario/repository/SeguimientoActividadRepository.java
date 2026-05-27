package com.centrocomunitario.repository;

import com.centrocomunitario.model.SeguimientoActividad;
import com.centrocomunitario.model.SeguimientoActividadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeguimientoActividadRepository extends JpaRepository<SeguimientoActividad, SeguimientoActividadId> {

    @Query(value = "SELECT * FROM Seguimientoactividad WHERE idUsuario = :idUsuario AND idInstructor = :idInstructor AND idParticipante = :idParticipante AND idActividad = :idActividad AND fecha_registro = :fechaRegistro", nativeQuery = true)
    Optional<SeguimientoActividad> findByClaveCompuesta(
            @Param("idUsuario") Integer idUsuario,
            @Param("idInstructor") Integer idInstructor,
            @Param("idParticipante") Integer idParticipante,
            @Param("idActividad") Integer idActividad,
            @Param("fechaRegistro") LocalDateTime fechaRegistro);

    // Por usuario (quien registra)
    List<SeguimientoActividad> findByIdUsuario(Integer idUsuario);

    // Por instructor
    List<SeguimientoActividad> findByIdInstructor(Integer idInstructor);

    // Por participante
    List<SeguimientoActividad> findByIdParticipante(Integer idParticipante);

    // Por actividad
    List<SeguimientoActividad> findByIdActividad(Integer idActividad);

    // Por instructor y actividad
    List<SeguimientoActividad> findByIdInstructorAndIdActividad(Integer idInstructor, Integer idActividad);

    // Por participante y actividad
    List<SeguimientoActividad> findByIdParticipanteAndIdActividad(Integer idParticipante, Integer idActividad);

    // Por instructor y participante
    List<SeguimientoActividad> findByIdInstructorAndIdParticipante(Integer idInstructor, Integer idParticipante);

    // Por usuario y actividad
    List<SeguimientoActividad> findByIdUsuarioAndIdActividad(Integer idUsuario, Integer idActividad);

    // Por rango de fechas
    List<SeguimientoActividad> findByFechaRegistroBetween(LocalDateTime inicio, LocalDateTime fin);

    // Por nivel de progreso
    List<SeguimientoActividad> findByNivelProgreso(String nivelProgreso);

    // Por aspecto evaluado (búsqueda parcial)
    List<SeguimientoActividad> findByAspectoEvaluadoContainingIgnoreCase(String aspecto);
}
