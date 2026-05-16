package com.gym.api.controller;

import com.gym.api.dto.request.ClaseDiaRequestDTO;
import com.gym.api.dto.response.ClaseDiaResponseDTO;
import com.gym.api.service.ClaseDiaService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de la
 * programacion semanal de clases.
 */
@RestController
@RequestMapping("/api/clase-dias")
@RequiredArgsConstructor
@Tag(name = "Clase-Dias", description = "Programacion semanal de clases por dia")
public class ClaseDiaController {

    private final ClaseDiaService claseDiaService;

    @PostMapping
    @Operation(summary = "Programar una clase en un dia de la semana")
    public ResponseEntity<ClaseDiaResponseDTO> crear(@Valid @RequestBody ClaseDiaRequestDTO request) {
        ClaseDiaResponseDTO creado = claseDiaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una programacion por su identificador")
    public ResponseEntity<ClaseDiaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(claseDiaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las programaciones, incluidas las dadas de baja")
    public ResponseEntity<List<ClaseDiaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(claseDiaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las programaciones activas")
    public ResponseEntity<List<ClaseDiaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(claseDiaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una programacion existente")
    public ResponseEntity<ClaseDiaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClaseDiaRequestDTO request) {
        return ResponseEntity.ok(claseDiaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una programacion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        claseDiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
