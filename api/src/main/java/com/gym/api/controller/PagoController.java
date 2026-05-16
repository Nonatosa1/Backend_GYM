package com.gym.api.controller;

import com.gym.api.dto.request.PagoRequestDTO;
import com.gym.api.dto.response.PagoResponseDTO;
import com.gym.api.service.PagoService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de pagos
 * de membresia.
 */
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Gestion de pagos asociados a inscripciones de membresia")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago de membresia")
    public ResponseEntity<PagoResponseDTO> crear(@Valid @RequestBody PagoRequestDTO request) {
        PagoResponseDTO creado = pagoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un pago por su identificador")
    public ResponseEntity<PagoResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos, incluidos los dados de baja")
    public ResponseEntity<List<PagoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los pagos activos")
    public ResponseEntity<List<PagoResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(pagoService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago existente")
    public ResponseEntity<PagoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PagoRequestDTO request) {
        return ResponseEntity.ok(pagoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente un pago")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
