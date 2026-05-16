package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO para crear o actualizar una inscripcion de membresia.
 *
 * Una inscripcion representa el acto por el cual un usuario adquiere una
 * membresia durante un periodo definido. Esta entidad es el punto de
 * partida del ciclo financiero del gimnasio: cada inscripcion genera un
 * compromiso de pago que se materializa en el modulo Pago, y ese pago
 * puede dividirse en multiples abonos que se registran en DetallePago.
 *
 * Las fechas se modelan como LocalDate (solo dia, sin hora) porque la
 * vigencia de una membresia se mide en dias completos. Una membresia
 * que "vence el 15 de junio" cubre todo ese dia sin importar la hora.
 *
 * La unica validacion semantica es la coherencia entre fechas: la fecha
 * de vencimiento debe ser posterior a la de inicio. Se permite cualquier
 * fecha de inicio (incluso pasada o futura) para dar flexibilidad al
 * sistema, ya que el negocio podria necesitar registrar inscripciones
 * historicas o programadas a futuro.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una inscripcion de membresia")
public class InscripcionRequestDTO {

    @NotNull(message = "El identificador de la membresia es obligatorio")
    @Schema(description = "Identificador de la membresia que se adquiere", example = "1")
    private Short idMembresia;

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario que se inscribe", example = "1")
    private Integer idUsuario;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Fecha en que comienza la vigencia de la inscripcion", example = "2026-05-12")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Schema(description = "Fecha en que finaliza la vigencia de la inscripcion", example = "2026-06-12")
    private LocalDate fechaVencimiento;
}
