package com.portfolio.campanario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReservaRequest {

    @NotNull(message = "El ID de la actividad es obligatorio")
    private Long actividadId;

    @Min(value = 1, message = "Debes reservar al menos 1 persona")
    private Integer numPersonas;

    public Long getActividadId() { return actividadId; }
    public void setActividadId(Long actividadId) { this.actividadId = actividadId; }

    public Integer getNumPersonas() { return numPersonas; }
    public void setNumPersonas(Integer numPersonas) { this.numPersonas = numPersonas; }
}