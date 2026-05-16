package com.gym.api.controller;

import com.gym.api.dto.request.PermisoAccesoRequestDTO;
import com.gym.api.dto.response.PermisoAccesoResponseDTO;
import com.gym.api.service.PermisoAccesoService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de permisos
 * de acceso.
 *
 * Los posibles codigos de respuesta para las operaciones de creacion y
 * actualizacion incluyen 404 cuando el usuario o el area referenciados
 * no existen, y 422 cuando las fechas son inconsistentes (fecha de fin
 * anterior o igual a fecha de inicio). Ambas situaciones son manejadas
 * automaticamente por el GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/permisos-acceso")
@RequiredArgsConstructor
@Tag(name = "Permisos de Acceso", description = "Gestion de permisos de acceso a areas del gimnasio")
public class PermisoAccesoController {

    private final PermisoAccesoService permisoAccesoService;

    @PostMapping
    @Operation(summary = "Otorgar un nuevo permiso de acceso")
    public ResponseEntity<PermisoAccesoResponseDTO> crear(@Valid @RequestBody PermisoAccesoRequestDTO request) {
        PermisoAccesoResponseDTO creado = permisoAccesoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un permiso de acceso por su identificador")
    public ResponseEntity<PermisoAccesoResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(permisoAccesoService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los permisos de acceso, incluidos los dados de baja")
    public ResponseEntity<List<PermisoAccesoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(permisoAccesoService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los permisos de acceso activos")
    public ResponseEntity<List<PermisoAccesoResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(permisoAccesoService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un permiso de acceso existente")
    public ResponseEntity<PermisoAccesoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PermisoAccesoRequestDTO request) {
        return ResponseEntity.ok(permisoAccesoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Revocar logicamente un permiso de acceso")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        permisoAccesoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
