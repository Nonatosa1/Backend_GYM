package com.gym.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de una transacción individual")
public class TransaccionDTO {

    @Schema(description = "Identificador único de la transacción", example = "1542")
    private Integer id;

    @Schema(description = "Fecha y hora en que se efectuó la transacción", example = "2026-03-15T10:32:00")
    private LocalDateTime fecha;

    @Schema(description = "Nombre completo del miembro que realizó la transacción", example = "Juan Pérez Gómez")
    private String nombreMiembro;

    @Schema(description = "Concepto o descripción de la transacción", example = "Membresía: Premium")
    private String concepto;

    @Schema(description = "Monto cobrado en esta transacción", example = "850.00")
    private BigDecimal monto;
}
