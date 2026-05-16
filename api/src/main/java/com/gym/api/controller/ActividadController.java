package com.gym.api.controller;

import com.gym.api.dto.request.ActividadRequestDTO;
import com.gym.api.dto.response.ActividadResponseDTO;
import com.gym.api.service.ActividadService;
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
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
@Tag(name = "Actividades", description = "Gestion del catalogo de actividades fisicas")
public class ActividadController {

    private final ActividadService actividadService;

    @PostMapping
    @Operation(summary = "Crear una nueva actividad")
    public ResponseEntity<ActividadResponseDTO> crear(@Valid @RequestBody ActividadRequestDTO request) {
        ActividadResponseDTO creada = actividadService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una actividad por su identificador")
    public ResponseEntity<ActividadResponseDTO> consultarPorId(@PathVariable Byte id) {
        return ResponseEntity.ok(actividadService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las actividades, incluidas las dadas de baja")
    public ResponseEntity<List<ActividadResponseDTO>> listarTodos() {
        return ResponseEntity.ok(actividadService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las actividades activas")
    public ResponseEntity<List<ActividadResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(actividadService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una actividad existente")
    public ResponseEntity<ActividadResponseDTO> actualizar(@PathVariable Byte id, @Valid @RequestBody ActividadRequestDTO request) {
        return ResponseEntity.ok(actividadService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una actividad")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
