package com.portfolio.campanario.service;

import com.portfolio.campanario.model.Actividad;
import com.portfolio.campanario.repository.ActividadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;

    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    // CREATE
    public Actividad crearActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    // READ (All) - Ordenado para el calendario
    public List<Actividad> obtenerTodasLasActividades() {
        return actividadRepository.findAllByOrderByFechaAscHoraInicioAsc();
    }

    // READ (One)
    public Actividad obtenerActividadPorId(Long id) {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + id));
    }

    // UPDATE
    public Actividad actualizarActividad(Long id, Actividad detalles) {
        Actividad actividad = obtenerActividadPorId(id);

        actividad.setFecha(detalles.getFecha());
        actividad.setHoraInicio(detalles.getHoraInicio());
        actividad.setHoraFin(detalles.getHoraFin());
        actividad.setCapacidadMaxima(detalles.getCapacidadMaxima());
        actividad.setEstado(detalles.getEstado());

        return actividadRepository.save(actividad);
    }

    // DELETE (Soft)
    public void eliminarActividad(Long id) {
        Actividad actividad = obtenerActividadPorId(id);
        actividad.setEstado(Actividad.EstadoActividad.CANCELADO);
        actividadRepository.save(actividad);
    }
}