package com.portfolio.campanario.controller;

import com.portfolio.campanario.model.Actividad;
import com.portfolio.campanario.service.ActividadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@CrossOrigin(origins = "*")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @PostMapping
    public ResponseEntity<Actividad> crear(@RequestBody Actividad actividad) {
        return ResponseEntity.ok(actividadService.crearActividad(actividad));
    }

    @GetMapping
    public ResponseEntity<List<Actividad>> obtenerTodas() {
        return ResponseEntity.ok(actividadService.obtenerTodasLasActividades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actividad> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(actividadService.obtenerActividadPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> actualizar(@PathVariable Long id, @RequestBody Actividad detalles) {
        return ResponseEntity.ok(actividadService.actualizarActividad(id, detalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return ResponseEntity.noContent().build(); 
    }
}