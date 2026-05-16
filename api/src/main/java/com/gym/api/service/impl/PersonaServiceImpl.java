package com.gym.api.service.impl;

import com.gym.api.dto.request.PersonaRequestDTO;
import com.gym.api.dto.response.PersonaResponseDTO;
import com.gym.api.entity.Persona;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.PersonaMapper;
import com.gym.api.repository.PersonaRepository;
import com.gym.api.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato definido en PersonaService.
 *
 * Sigue el mismo patron de los servicios de catalogos: gestiona el ciclo
 * CRUD completo de la entidad, aplica las reglas de auditoria automatica
 * en alta y baja, y delega la conversion entre entidad y DTOs en el
 * mapper correspondiente.
 *
 * Una nota de diseño: el metodo consultarPorId devuelve la persona sin
 * filtrar por habilitado. Esto significa que se puede consultar incluso
 * personas dadas de baja logica. Esta decision aporta flexibilidad para
 * casos de auditoria o de consultar historicos. Si en el futuro se
 * necesita que las consultas no expongan registros dados de baja, ese
 * filtrado puede hacerse facilmente añadiendo una verificacion despues
 * de obtener la entidad.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @Override
    public PersonaResponseDTO crear(PersonaRequestDTO request) {
        Persona entidad = personaMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Persona guardada = personaRepository.save(entidad);
        return personaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDTO consultarPorId(Integer id) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));
        return personaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> listarTodos() {
        return personaRepository.findAll()
                .stream()
                .map(personaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> listarHabilitados() {
        return personaRepository.findAll()
                .stream()
                .filter(Persona::getHabilitado)
                .map(personaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PersonaResponseDTO actualizar(Integer id, PersonaRequestDTO request) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));

        personaMapper.updateEntity(entidad, request);

        Persona actualizada = personaRepository.save(entidad);
        return personaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        personaRepository.save(entidad);
    }
}
