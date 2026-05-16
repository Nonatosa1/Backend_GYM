package com.gym.api.controller;

import com.gym.api.dto.request.InscripcionRequestDTO;
import com.gym.api.dto.response.InscripcionResponseDTO;
import com.gym.api.service.InscripcionService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de
 * inscripciones de membresia.
 */
@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@Tag(name = "Inscripciones", description = "Gestion de inscripciones de usuarios a membresias")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    @Operation(summary = "Crear una nueva inscripcion")
    public ResponseEntity<InscripcionResponseDTO> crear(@Valid @RequestBody InscripcionRequestDTO request) {
        InscripcionResponseDTO creada = inscripcionService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una inscripcion por su identificador")
    public ResponseEntity<InscripcionResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(inscripcionService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las inscripciones, incluidas las dadas de baja")
    public ResponseEntity<List<InscripcionResponseDTO>> listarTodos() {
        return ResponseEntity.ok(inscripcionService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las inscripciones activas")
    public ResponseEntity<List<InscripcionResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(inscripcionService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una inscripcion existente")
    public ResponseEntity<InscripcionResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody InscripcionRequestDTO request) {
        return ResponseEntity.ok(inscripcionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente una inscripcion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
