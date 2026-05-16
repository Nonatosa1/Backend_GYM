package com.gym.api.controller;

import com.gym.api.dto.request.AsistenciaRequestDTO;
import com.gym.api.dto.response.AsistenciaResponseDTO;
import com.gym.api.service.AsistenciaService;
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

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
@Tag(name = "Asistencias", description = "Registro de asistencias a sesiones de clases")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @PostMapping
    @Operation(summary = "Registrar una asistencia a una clase")
    public ResponseEntity<AsistenciaResponseDTO> crear(@Valid @RequestBody AsistenciaRequestDTO request) {
        AsistenciaResponseDTO creada = asistenciaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una asistencia por su identificador")
    public ResponseEntity<AsistenciaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(asistenciaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las asistencias, incluidas las dadas de baja")
    public ResponseEntity<List<AsistenciaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(asistenciaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las asistencias activas")
    public ResponseEntity<List<AsistenciaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(asistenciaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una asistencia existente")
    public ResponseEntity<AsistenciaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody AsistenciaRequestDTO request) {
        return ResponseEntity.ok(asistenciaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una asistencia")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
