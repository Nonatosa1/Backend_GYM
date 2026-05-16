package com.gym.api.controller;

import com.gym.api.dto.request.PagoServiciosRequestDTO;
import com.gym.api.dto.response.PagoServiciosResponseDTO;
import com.gym.api.service.PagoServiciosService;
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
 * de servicios.
 *
 * Este controlador cierra el ciclo financiero del bloque de inventario.
 * Cada pago referencia a una renta de servicios especifica y registra el
 * metodo de pago utilizado, completando asi el modelo completo de
 * gestion comercial de equipos del gimnasio.
 */
@RestController
@RequestMapping("/api/pagos-servicios")
@RequiredArgsConstructor
@Tag(name = "Pagos de Servicios", description = "Gestion de pagos asociados a rentas de servicios")
public class PagoServiciosController {

    private final PagoServiciosService pagoServiciosService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago de servicios")
    public ResponseEntity<PagoServiciosResponseDTO> crear(@Valid @RequestBody PagoServiciosRequestDTO request) {
        PagoServiciosResponseDTO creado = pagoServiciosService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un pago por su identificador")
    public ResponseEntity<PagoServiciosResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoServiciosService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos, incluidos los dados de baja")
    public ResponseEntity<List<PagoServiciosResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pagoServiciosService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los pagos activos")
    public ResponseEntity<List<PagoServiciosResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(pagoServiciosService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago existente")
    public ResponseEntity<PagoServiciosResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PagoServiciosRequestDTO request) {
        return ResponseEntity.ok(pagoServiciosService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente un pago")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pagoServiciosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
