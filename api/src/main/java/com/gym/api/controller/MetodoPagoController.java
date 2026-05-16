package com.gym.api.controller;

import com.gym.api.dto.request.MetodoPagoRequestDTO;
import com.gym.api.dto.response.MetodoPagoResponseDTO;
import com.gym.api.service.MetodoPagoService;
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
@RequestMapping("/api/metodos-pago")
@RequiredArgsConstructor
@Tag(name = "Metodos de Pago", description = "Gestion del catalogo de metodos de pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo metodo de pago")
    public ResponseEntity<MetodoPagoResponseDTO> crear(@Valid @RequestBody MetodoPagoRequestDTO request) {
        MetodoPagoResponseDTO creado = metodoPagoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un metodo de pago por su identificador")
    public ResponseEntity<MetodoPagoResponseDTO> consultarPorId(@PathVariable Byte id) {
        return ResponseEntity.ok(metodoPagoService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los metodos de pago, incluidos los dados de baja")
    public ResponseEntity<List<MetodoPagoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(metodoPagoService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los metodos de pago activos")
    public ResponseEntity<List<MetodoPagoResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(metodoPagoService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un metodo de pago existente")
    public ResponseEntity<MetodoPagoResponseDTO> actualizar(@PathVariable Byte id, @Valid @RequestBody MetodoPagoRequestDTO request) {
        return ResponseEntity.ok(metodoPagoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un metodo de pago")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        metodoPagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
