package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una asistencia devueltos por la API")
public class AsistenciaResponseDTO {

    @Schema(description = "Identificador unico de la asistencia", example = "1")
    private Integer idAsistencia;

    @Schema(description = "Clase a la que corresponde la asistencia")
    private ClaseResponseDTO clase;

    @Schema(description = "Usuario cuya asistencia se registra")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Fecha de la sesion", example = "2026-05-12")
    private LocalDate fecha;

    @Schema(description = "Indica si el usuario asistio a la sesion", example = "true")
    private Boolean asistio;

    @Schema(description = "Indica si el registro esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
