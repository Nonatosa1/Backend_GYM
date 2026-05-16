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
 * DTO que representa los datos de informacion medica devueltos por la API.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de informacion medica devueltos por la API")
public class InformacionMedicaResponseDTO {

    @Schema(description = "Identificador unico de la informacion medica", example = "1")
    private Integer idInformacionMedica;

    @Schema(description = "Usuario al que pertenece la informacion medica")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Tipo de sangre", example = "O+")
    private String tipoSangre;

    @Schema(description = "Alergias conocidas", example = "Polen, mariscos")
    private String alergias;

    @Schema(description = "Condiciones de salud", example = "Hipertension controlada")
    private String condicionesDeSalud;

    @Schema(description = "Telefono de emergencia", example = "5512345678")
    private String telefonoEmergencia;

    @Schema(description = "Indica si el registro esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
