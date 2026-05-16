package com.gym.api.controller;

import com.gym.api.dto.request.RolRequestDTO;
import com.gym.api.dto.response.RolResponseDTO;
import com.gym.api.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST que expone los endpoints HTTP de gestion de roles.
 *
 * Este controlador es intencionalmente delgado: cada metodo recibe la
 * peticion, delega al servicio correspondiente, y devuelve la respuesta.
 * No contiene logica de negocio ni validaciones complejas; esas
 * responsabilidades viven en el servicio y en las anotaciones de
 * validacion del DTO de peticion.
 *
 * Anotaciones importantes:
 *
 * @RestController combina @Controller y @ResponseBody, indicando que los
 * valores devueltos por los metodos se serializan directamente como JSON
 * en la respuesta HTTP.
 *
 * @RequestMapping define el prefijo comun para todas las rutas del
 * controlador. En este caso, todos los endpoints empiezan con /api/roles.
 *
 * @RequiredArgsConstructor de Lombok genera el constructor para la
 * inyeccion de dependencias por constructor.
 *
 * @Tag de SpringDoc agrupa estos endpoints bajo una seccion visual en
 * Swagger UI, facilitando la navegacion de la documentacion.
 *
 * @Operation describe que hace cada endpoint individualmente.
 */
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestion de roles del sistema")
public class RolController {

    private final RolService rolService;

    /**
     * Crea un nuevo rol. Devuelve HTTP 201 (Created) con el rol creado
     * en el cuerpo de la respuesta.
     *
     * La anotacion @Valid en el parametro activa las validaciones del
     * DTO (@NotBlank, @Size, etc.). Si los datos no cumplen, Spring lanza
     * una MethodArgumentNotValidException que el GlobalExceptionHandler
     * convierte en una respuesta HTTP 400 con los errores detallados.
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo rol")
    public ResponseEntity<RolResponseDTO> crear(@Valid @RequestBody RolRequestDTO request) {
        RolResponseDTO creado = rolService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Consulta un rol por su ID. Si no existe, el servicio lanza
     * ResourceNotFoundException y el GlobalExceptionHandler la convierte
     * en una respuesta HTTP 404.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Consultar un rol por su identificador")
    public ResponseEntity<RolResponseDTO> consultarPorId(@PathVariable Byte id) {
        RolResponseDTO rol = rolService.consultarPorId(id);
        return ResponseEntity.ok(rol);
    }

    /**
     * Devuelve la lista completa de roles, incluidos los dados de baja.
     */
    @GetMapping
    @Operation(summary = "Listar todos los roles, incluidos los dados de baja")
    public ResponseEntity<List<RolResponseDTO>> listarTodos() {
        return ResponseEntity.ok(rolService.listarTodos());
    }

    /**
     * Devuelve solo los roles activos. Es el endpoint que se usara con mas
     * frecuencia en la operacion normal del sistema.
     */
    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los roles activos")
    public ResponseEntity<List<RolResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(rolService.listarHabilitados());
    }

    /**
     * Actualiza un rol existente.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un rol existente")
    public ResponseEntity<RolResponseDTO> actualizar(@PathVariable Byte id, @Valid @RequestBody RolRequestDTO request) {
        RolResponseDTO actualizado = rolService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina logicamente un rol. Devuelve HTTP 204 (No Content) porque
     * la operacion fue exitosa pero no hay datos que devolver.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un rol")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
