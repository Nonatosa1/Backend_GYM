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
 * DTO que representa los datos de un permiso de acceso devueltos por la API.
 *
 * Incluye informacion anidada tanto del usuario como del area para que el
 * cliente reciba en una sola peticion todos los datos relevantes. Esto es
 * particularmente util cuando se listan permisos para una vista
 * administrativa, donde se quiere ver quien tiene acceso a que sin tener
 * que hacer peticiones adicionales por cada permiso.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un permiso de acceso devueltos por la API")
public class PermisoAccesoResponseDTO {

    @Schema(description = "Identificador unico del permiso", example = "1")
    private Integer idPermisoAcceso;

    @Schema(description = "Usuario al que se otorga el permiso")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Area sobre la que se otorga el permiso")
    private AreaResponseDTO area;

    @Schema(description = "Momento en que comienza la vigencia del permiso", example = "2026-05-12T08:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Momento en que finaliza la vigencia del permiso", example = "2026-05-12T20:00:00")
    private LocalDateTime fechaFin;

    @Schema(description = "Indica si el permiso esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
