package com.gym.api.controller;

import com.gym.api.dto.request.DetallePagoRequestDTO;
import com.gym.api.dto.response.DetallePagoResponseDTO;
import com.gym.api.service.DetallePagoService;
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
@RequestMapping("/api/detalles-pago")
@RequiredArgsConstructor
@Tag(name = "Detalles de Pago", description = "Gestion de abonos parciales aplicados a pagos")
public class DetallePagoController {

    private final DetallePagoService detallePagoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo abono")
    public ResponseEntity<DetallePagoResponseDTO> crear(@Valid @RequestBody DetallePagoRequestDTO request) {
        DetallePagoResponseDTO creado = detallePagoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un abono por su identificador")
    public ResponseEntity<DetallePagoResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detallePagoService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los abonos, incluidos los dados de baja")
    public ResponseEntity<List<DetallePagoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(detallePagoService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los abonos activos")
    public ResponseEntity<List<DetallePagoResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(detallePagoService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un abono existente")
    public ResponseEntity<DetallePagoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody DetallePagoRequestDTO request) {
        return ResponseEntity.ok(detallePagoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente un abono")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        detallePagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
