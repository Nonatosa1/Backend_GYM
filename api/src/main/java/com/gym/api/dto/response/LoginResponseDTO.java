package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos del usuario autenticado")
public class LoginResponseDTO {

    @Schema(description = "Nombre de cuenta del usuario", example = "juan_perez")
    private String usuario;

    @Schema(description = "Nombre completo de la persona", example = "Juan Carlos Garcia Lopez")
    private String nombre;

    @Schema(description = "Rol del usuario en formato texto", example = "Administrador")
    private String rol;
}
