package com.gym.api.service.impl;

import com.gym.api.dto.request.AgendaRequestDTO;
import com.gym.api.dto.response.AgendaResponseDTO;
import com.gym.api.entity.Agenda;
import com.gym.api.entity.Area;
import com.gym.api.entity.EspacioFisico;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.AgendaMapper;
import com.gym.api.repository.AgendaRepository;
import com.gym.api.repository.AreaRepository;
import com.gym.api.repository.EspacioFisicoRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato AgendaService.
 *
 * Esta implementacion es la mas rica del bloque y, posiblemente, una de
 * las mas representativas de la complejidad real que vas a encontrar en
 * el resto del proyecto. Coordina cuatro responsabilidades simultaneas:
 *
 *   Resolver tres relaciones distintas a partir de sus IDs, validando que
 *   cada entidad referenciada exista en la base de datos.
 *
 *   Validar la consistencia temporal: que la fecha de fin sea posterior a
 *   la fecha de inicio.
 *
 *   Validar la coherencia entre el area y el espacio fisico: el espacio
 *   fisico declarado debe pertenecer al area declarada. Esta verificacion
 *   es necesaria porque el diseño del diagrama mantiene ambas relaciones
 *   por separado, lo cual permite consultas eficientes pero impone esta
 *   responsabilidad al servicio.
 *
 *   Gestionar la auditoria automatica, como todos los servicios del proyecto.
 *
 * El orden de las validaciones sigue el principio "fail fast" aplicado en
 * grado: primero las verificaciones mas baratas (comparacion de fechas en
 * memoria), despues las que requieren consultas a la base de datos, y
 * finalmente la validacion de coherencia que necesita los datos ya cargados.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final EspacioFisicoRepository espacioFisicoRepository;
    private final AgendaMapper agendaMapper;

    @Override
    public AgendaResponseDTO crear(AgendaRequestDTO request) {
        // Primera validacion: consistencia de fechas. Barata y rapida.
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        // Resolucion de las tres relaciones, fallando rapido si alguna no existe.
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        EspacioFisico espacioFisico = espacioFisicoRepository.findById(request.getIdEspacioFisico())
                .orElseThrow(() -> new ResourceNotFoundException("EspacioFisico", request.getIdEspacioFisico()));

        // Validacion de coherencia: el espacio fisico debe pertenecer al area declarada.
        // Esta validacion solo puede hacerse despues de cargar las entidades porque
        // necesitamos comparar el area del espacio fisico contra el area declarada.
        validarCoherenciaAreaEspacio(area, espacioFisico);

        Agenda entidad = agendaMapper.toEntity(request);
        entidad.setUsuario(usuario);
        entidad.setArea(area);
        entidad.setEspacioFisico(espacioFisico);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Agenda guardada = agendaRepository.save(entidad);
        return agendaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public AgendaResponseDTO consultarPorId(Integer id) {
        Agenda entidad = agendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agenda", id));
        return agendaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarTodos() {
        return agendaRepository.findAll()
                .stream()
                .map(agendaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarHabilitados() {
        return agendaRepository.findAll()
                .stream()
                .filter(Agenda::getHabilitado)
                .map(agendaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public AgendaResponseDTO actualizar(Integer id, AgendaRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        Agenda entidad = agendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agenda", id));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        EspacioFisico espacioFisico = espacioFisicoRepository.findById(request.getIdEspacioFisico())
                .orElseThrow(() -> new ResourceNotFoundException("EspacioFisico", request.getIdEspacioFisico()));

        validarCoherenciaAreaEspacio(area, espacioFisico);

        agendaMapper.updateEntity(entidad, request);
        entidad.setUsuario(usuario);
        entidad.setArea(area);
        entidad.setEspacioFisico(espacioFisico);

        Agenda actualizada = agendaRepository.save(entidad);
        return agendaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Agenda entidad = agendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agenda", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        agendaRepository.save(entidad);
    }

    /**
     * Verifica que la fecha de fin sea posterior a la fecha de inicio.
     * Mismo patron de validacion que usamos en PermisoAccesoServiceImpl.
     */
    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new BusinessException(
                    "La fecha de fin debe ser posterior a la fecha de inicio"
            );
        }
    }

    /**
     * Verifica que el espacio fisico declarado pertenezca al area declarada.
     *
     * Esta es una validacion de integridad del dominio que el modelo de
     * datos por si solo no puede expresar, porque la entidad Agenda tiene
     * relaciones separadas a Area y EspacioFisico (aunque conceptualmente
     * un EspacioFisico ya pertenece a un Area).
     *
     * La validacion compara el ID del area declarada con el ID del area
     * a la que pertenece el espacio fisico. Si no coinciden, lanza una
     * BusinessException que se traduce en HTTP 422.
     */
    private void validarCoherenciaAreaEspacio(Area area, EspacioFisico espacioFisico) {
        if (!area.getIdArea().equals(espacioFisico.getArea().getIdArea())) {
            throw new BusinessException(
                    "El espacio fisico no pertenece al area declarada"
            );
        }
    }
}
