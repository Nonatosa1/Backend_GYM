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
 * DTO que representa los datos de una entrada de agenda devueltos por la API.
 *
 * Esta es la respuesta mas rica del bloque porque incluye tres entidades
 * anidadas: usuario, area y espacio fisico. Aunque podria parecer
 * excesivo, esta riqueza permite al cliente mostrar una vista completa de
 * la reserva sin hacer peticiones adicionales, lo cual es especialmente
 * valioso para pantallas de calendario o detalle de reserva.
 *
 * Como el costo de cargar estas relaciones puede crecer si se listan
 * muchas agendas, en una futura optimizacion podriamos crear un DTO
 * "ligero" alternativo que solo incluya los identificadores, dejando este
 * DTO completo para vistas de detalle individuales.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una entrada de agenda devueltos por la API")
public class AgendaResponseDTO {

    @Schema(description = "Identificador unico de la entrada de agenda", example = "1")
    private Integer idAgenda;

    @Schema(description = "Descripcion de la reserva", example = "Sesion de entrenamiento personal")
    private String agenda;

    @Schema(description = "Usuario que realizo la reserva")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Area donde se realizara la actividad")
    private AreaResponseDTO area;

    @Schema(description = "Espacio fisico concreto reservado")
    private EspacioFisicoResponseDTO espacioFisico;

    @Schema(description = "Momento de inicio de la reserva", example = "2026-05-12T18:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Momento de fin de la reserva", example = "2026-05-12T19:00:00")
    private LocalDateTime fechaFin;

    @Schema(description = "Indica si la entrada de agenda esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
