package com.gym.api.controller;

import com.gym.api.dto.request.AgendaRequestDTO;
import com.gym.api.dto.response.AgendaResponseDTO;
import com.gym.api.service.AgendaService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de entradas
 * de agenda.
 *
 * Los posibles codigos de respuesta para las operaciones de creacion y
 * actualizacion incluyen 404 cuando alguna de las tres entidades
 * referenciadas (usuario, area, espacio fisico) no existe, y 422 cuando
 * las fechas son inconsistentes o cuando el espacio fisico no pertenece al
 * area declarada. Todas estas situaciones son manejadas automaticamente
 * por el GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/agendas")
@RequiredArgsConstructor
@Tag(name = "Agendas", description = "Gestion de reservas y citas en el gimnasio")
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    @Operation(summary = "Crear una nueva entrada de agenda")
    public ResponseEntity<AgendaResponseDTO> crear(@Valid @RequestBody AgendaRequestDTO request) {
        AgendaResponseDTO creada = agendaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una entrada de agenda por su identificador")
    public ResponseEntity<AgendaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(agendaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las entradas de agenda, incluidas las dadas de baja")
    public ResponseEntity<List<AgendaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las entradas de agenda activas")
    public ResponseEntity<List<AgendaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(agendaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una entrada de agenda existente")
    public ResponseEntity<AgendaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody AgendaRequestDTO request) {
        return ResponseEntity.ok(agendaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente una entrada de agenda")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        agendaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
