package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un metodo de pago devueltos por la API")
public class MetodoPagoResponseDTO {

    @Schema(description = "Identificador unico del metodo de pago", example = "1")
    private Byte idMetodoPago;

    @Schema(description = "Nombre del metodo de pago", example = "Tarjeta de credito")
    private String metodoPago;

    @Schema(description = "Descripcion del metodo de pago", example = "Visa, Mastercard y American Express")
    private String descripcion;

    @Schema(description = "Indica si el metodo de pago esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
