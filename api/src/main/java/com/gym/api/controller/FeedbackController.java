package com.gym.api.controller;

import com.gym.api.dto.request.FeedbackRequestDTO;
import com.gym.api.dto.response.FeedbackResponseDTO;
import com.gym.api.service.FeedbackService;
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
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedbacks", description = "Opiniones de usuarios sobre las clases")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(summary = "Registrar un feedback sobre una clase")
    public ResponseEntity<FeedbackResponseDTO> crear(@Valid @RequestBody FeedbackRequestDTO request) {
        FeedbackResponseDTO creado = feedbackService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un feedback por su identificador")
    public ResponseEntity<FeedbackResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los feedbacks, incluidos los dados de baja")
    public ResponseEntity<List<FeedbackResponseDTO>> listarTodos() {
        return ResponseEntity.ok(feedbackService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los feedbacks activos")
    public ResponseEntity<List<FeedbackResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(feedbackService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un feedback existente")
    public ResponseEntity<FeedbackResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody FeedbackRequestDTO request) {
        return ResponseEntity.ok(feedbackService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un feedback")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        feedbackService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
