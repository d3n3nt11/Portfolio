package com.portfolio.campanario.controller;

import com.portfolio.campanario.dto.ReservaRequest;
import com.portfolio.campanario.model.Reserva;
import com.portfolio.campanario.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(
            @RequestParam Long usuarioId,
            @Valid @RequestBody ReservaRequest request) {

        try {
            Reserva reserva = reservaService.crearReserva(usuarioId, request);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}