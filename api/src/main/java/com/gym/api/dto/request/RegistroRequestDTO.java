package com.gym.api.dto.request;

import com.gym.api.entity.Persona;
import com.gym.api.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para registrar un usuario")
public class RegistroRequestDTO {
    private PersonaRequestDTO persona;
    private UsuarioRequestDTO usuario;
    private InscripcionRequestDTO inscripcion;
    private PagoRequestDTO pago;
}
