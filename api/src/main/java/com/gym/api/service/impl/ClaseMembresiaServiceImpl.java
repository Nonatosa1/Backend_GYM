package com.gym.api.service.impl;

import com.gym.api.dto.request.ClaseMembresiaRequestDTO;
import com.gym.api.dto.response.ClaseMembresiaResponseDTO;
import com.gym.api.entity.Clase;
import com.gym.api.entity.ClaseMembresia;
import com.gym.api.entity.Membresia;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.ClaseMembresiaMapper;
import com.gym.api.repository.ClaseMembresiaRepository;
import com.gym.api.repository.ClaseRepository;
import com.gym.api.repository.MembresiaRepository;
import com.gym.api.service.ClaseMembresiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaseMembresiaServiceImpl implements ClaseMembresiaService {

    private final ClaseMembresiaRepository claseMembresiaRepository;
    private final ClaseRepository claseRepository;
    private final MembresiaRepository membresiaRepository;
    private final ClaseMembresiaMapper claseMembresiaMapper;

    @Override
    public ClaseMembresiaResponseDTO crear(ClaseMembresiaRequestDTO request) {
        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Membresia membresia = membresiaRepository.findById(request.getIdMembresia())
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", request.getIdMembresia()));

        ClaseMembresia entidad = claseMembresiaMapper.toEntity(request);
        entidad.setClase(clase);
        entidad.setMembresia(membresia);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        ClaseMembresia guardado = claseMembresiaRepository.save(entidad);
        return claseMembresiaMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClaseMembresiaResponseDTO consultarPorId(Integer id) {
        ClaseMembresia entidad = claseMembresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClaseMembresia", id));
        return claseMembresiaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseMembresiaResponseDTO> listarTodos() {
        return claseMembresiaRepository.findAll()
                .stream()
                .map(claseMembresiaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseMembresiaResponseDTO> listarHabilitados() {
        return claseMembresiaRepository.findAll()
                .stream()
                .filter(ClaseMembresia::getHabilitado)
                .map(claseMembresiaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ClaseMembresiaResponseDTO actualizar(Integer id, ClaseMembresiaRequestDTO request) {
        ClaseMembresia entidad = claseMembresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClaseMembresia", id));

        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Membresia membresia = membresiaRepository.findById(request.getIdMembresia())
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", request.getIdMembresia()));

        claseMembresiaMapper.updateEntity(entidad, request);
        entidad.setClase(clase);
        entidad.setMembresia(membresia);

        ClaseMembresia actualizado = claseMembresiaRepository.save(entidad);
        return claseMembresiaMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        ClaseMembresia entidad = claseMembresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClaseMembresia", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        claseMembresiaRepository.save(entidad);
    }
}
