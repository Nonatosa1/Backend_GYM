package com.gym.api.controller;

import com.gym.api.dto.request.ClaseRequestDTO;
import com.gym.api.dto.response.ClaseResponseDTO;
import com.gym.api.service.ClaseService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de clases.
 *
 * Los posibles codigos de respuesta para las operaciones de creacion y
 * actualizacion incluyen 404 cuando la persona responsable, el tipo de
 * clase o la actividad no existen, y 422 cuando los horarios son
 * inconsistentes (hora de termino anterior o igual a hora de inicio).
 * Todas estas situaciones son manejadas automaticamente por el
 * GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/clases")
@RequiredArgsConstructor
@Tag(name = "Clases", description = "Gestion de clases del gimnasio")
public class ClaseController {

    private final ClaseService claseService;

    @PostMapping
    @Operation(summary = "Crear una nueva clase")
    public ResponseEntity<ClaseResponseDTO> crear(@Valid @RequestBody ClaseRequestDTO request) {
        ClaseResponseDTO creada = claseService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una clase por su identificador")
    public ResponseEntity<ClaseResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(claseService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las clases, incluidas las dadas de baja")
    public ResponseEntity<List<ClaseResponseDTO>> listarTodos() {
        return ResponseEntity.ok(claseService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las clases activas")
    public ResponseEntity<List<ClaseResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(claseService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una clase existente")
    public ResponseEntity<ClaseResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClaseRequestDTO request) {
        return ResponseEntity.ok(claseService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una clase")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        claseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
