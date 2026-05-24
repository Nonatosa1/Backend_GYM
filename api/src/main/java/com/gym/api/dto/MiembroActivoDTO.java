package com.gym.api.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiembroActivoDTO {
    private Integer idUsuario;
    private String nombreCompleto;
    private String correo;
    private String membresia;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Long diasRestantes;
}
