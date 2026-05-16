package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO que representa los datos de una renta de servicios devueltos por la API.
 *
 * Incluye informacion anidada del usuario que renta y del equipo rentado.
 * En el futuro, cuando integremos la consulta de pagos asociados a una
 * renta, podriamos enriquecer este DTO con un campo adicional que liste
 * los pagos efectuados, o crear un endpoint especifico para consultar la
 * relacion entre rentas y pagos.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una renta de servicios devueltos por la API")
public class RentaServiciosResponseDTO {

    @Schema(description = "Identificador unico de la renta", example = "1")
    private Integer idRentaServicio;

    @Schema(description = "Usuario que renta el equipo")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Equipo de inventario rentado")
    private InventarioResponseDTO inventario;

    @Schema(description = "Momento en que comienza la renta", example = "2026-05-12T10:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Momento limite hasta el cual la renta es valida", example = "2026-05-12T12:00:00")
    private LocalDateTime fechaVence;

    @Schema(description = "Indica si la renta esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
