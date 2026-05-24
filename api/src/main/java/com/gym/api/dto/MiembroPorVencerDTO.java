package com.gym.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un miembro cuya membresía está próxima a vencer")
public class MiembroPorVencerDTO {

    @Schema(description = "Nombre completo del miembro", example = "Juan Pérez Gómez")
    private String nombreCompleto;

    @Schema(description = "Correo electrónico del miembro", example = "juan.perez@correo.com")
    private String correo;

    @Schema(description = "Tipo de membresía contratada", example = "Premium")
    private String tipoMembresia;

    @Schema(description = "Fecha exacta en que vence la membresía", example = "2026-05-28")
    private LocalDate fechaVencimiento;

    @Schema(description = "Días restantes para que venza la membresía", example = "7")
    private Long diasParaVencer;

    @Schema(
            description = "Estado de urgencia de la renovación",
            example = "URGENTE",
            allowableValues = {"URGENTE", "ESTA_SEMANA", "EN_PLAZO"}
    )
    private String estado;
}
