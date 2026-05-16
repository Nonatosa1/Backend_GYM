package com.gym.api.controller;

import com.gym.api.dto.request.EspacioFisicoRequestDTO;
import com.gym.api.dto.response.EspacioFisicoResponseDTO;
import com.gym.api.service.EspacioFisicoService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de espacios
 * fisicos.
 *
 * Los posibles codigos de respuesta para las operaciones de creacion y
 * actualizacion incluyen 404 cuando el area referenciada no existe, lo
 * cual es manejado automaticamente por el GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/espacios-fisicos")
@RequiredArgsConstructor
@Tag(name = "Espacios Fisicos", description = "Gestion de espacios fisicos del gimnasio")
public class EspacioFisicoController {

    private final EspacioFisicoService espacioFisicoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo espacio fisico")
    public ResponseEntity<EspacioFisicoResponseDTO> crear(@Valid @RequestBody EspacioFisicoRequestDTO request) {
        EspacioFisicoResponseDTO creado = espacioFisicoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un espacio fisico por su identificador")
    public ResponseEntity<EspacioFisicoResponseDTO> consultarPorId(@PathVariable Short id) {
        return ResponseEntity.ok(espacioFisicoService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los espacios fisicos, incluidos los dados de baja")
    public ResponseEntity<List<EspacioFisicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(espacioFisicoService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los espacios fisicos activos")
    public ResponseEntity<List<EspacioFisicoResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(espacioFisicoService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un espacio fisico existente")
    public ResponseEntity<EspacioFisicoResponseDTO> actualizar(@PathVariable Short id, @Valid @RequestBody EspacioFisicoRequestDTO request) {
        return ResponseEntity.ok(espacioFisicoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un espacio fisico")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        espacioFisicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
