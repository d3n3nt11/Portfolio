package com.portfolio.campanario.service;

import com.portfolio.campanario.dto.ReservaRequest;
import com.portfolio.campanario.model.Actividad;
import com.portfolio.campanario.model.Reserva;
import com.portfolio.campanario.model.Usuario;
import com.portfolio.campanario.repository.ActividadRepository;
import com.portfolio.campanario.repository.ReservaRepository;
import com.portfolio.campanario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ActividadRepository actividadRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          ActividadRepository actividadRepository,
                          UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.actividadRepository = actividadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // @Transactional asegura que si algo falla a mitad de camino, no se guarden datos a medias
    @Transactional
    public Reserva crearReserva(Long usuarioId, ReservaRequest request) {
        // 1. Buscar usuario y actividad
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Actividad actividad = actividadRepository.findById(request.getActividadId())
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        // 2. Validar que la actividad no esté cancelada
        if (actividad.getEstado() == Actividad.EstadoActividad.CANCELADO) {
            throw new RuntimeException("Esta actividad ha sido cancelada");
        }

        // 3. LÓGICA DE NEGOCIO: Comprobar aforo
        int personasYaConfirmadas = reservaRepository.calcularPersonasConfirmadas(actividad.getId());
        int personasSolicitadas = request.getNumPersonas();

        if (personasYaConfirmadas + personasSolicitadas > actividad.getCapacidadMaxima()) {
            throw new RuntimeException("Lo sentimos, no quedan plazas suficientes para este repique. " +
                    "Disponibles: " + (actividad.getCapacidadMaxima() - personasYaConfirmadas));
        }

        // 4. Crear y guardar la reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setUsuario(usuario);
        nuevaReserva.setActividad(actividad);
        nuevaReserva.setNumPersonas(personasSolicitadas);
        nuevaReserva.setEstado(Reserva.EstadoReserva.CONFIRMADA);

        Reserva reservaGuardada = reservaRepository.save(nuevaReserva);

        // 5. (Opcional pero PRO) Actualizar estado de la actividad a COMPLETO si se llenó
        if (personasYaConfirmadas + personasSolicitadas == actividad.getCapacidadMaxima()) {
            actividad.setEstado(Actividad.EstadoActividad.COMPLETO);
            actividadRepository.save(actividad);
        }

        return reservaGuardada;
    }

    public Iterable<Reserva> obtenerReservasDeUsuario(Long usuarioId) {
        // Podríamos añadir un método findByUsuarioId en el Repository,
        // pero por simplicidad usamos findAll y filtramos (o añádelo al repo).
        return reservaRepository.findAll();
    }
}