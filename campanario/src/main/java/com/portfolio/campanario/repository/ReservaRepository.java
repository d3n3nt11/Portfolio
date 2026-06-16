package com.portfolio.campanario.repository;

import com.portfolio.campanario.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    @Query("SELECT COALESCE(SUM(r.numPersonas), 0) FROM Reserva r WHERE r.actividad.id = :actividadId AND r.estado = 'CONFIRMADA'")
    int calcularPersonasConfirmadas(@Param("actividadId") Long actividadId);
}