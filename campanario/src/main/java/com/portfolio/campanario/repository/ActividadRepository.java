package com.portfolio.campanario.repository;

import com.portfolio.campanario.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {


    List<Actividad> findAllByOrderByFechaAscHoraInicioAsc();


    List<Actividad> findByFechaOrderByHoraInicioAsc(LocalDate fecha);
}