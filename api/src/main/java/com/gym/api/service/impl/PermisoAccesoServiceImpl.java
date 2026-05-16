package com.gym.api.service.impl;

import com.gym.api.dto.request.PermisoAccesoRequestDTO;
import com.gym.api.dto.response.PermisoAccesoResponseDTO;
import com.gym.api.entity.Area;
import com.gym.api.entity.PermisoAcceso;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.PermisoAccesoMapper;
import com.gym.api.repository.AreaRepository;
import com.gym.api.repository.PermisoAccesoRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.PermisoAccesoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato PermisoAccesoService.
 *
 * Esta implementacion introduce la primera validacion semantica del
 * proyecto que requiere comparar dos campos del DTO entre si: la fecha
 * de fin debe ser posterior a la fecha de inicio. Como las anotaciones
 * estandar de Bean Validation no tienen una forma directa de validar
 * relaciones entre campos, hacemos esta validacion explicitamente en el
 * servicio lanzando una BusinessException, que el GlobalExceptionHandler
 * convertira en una respuesta HTTP 422 (Unprocessable Entity).
 *
 * El metodo crear sigue el patron "fail fast":
 *   Primero valida la consistencia de las fechas porque es una verificacion
 *   barata que puede fallar inmediatamente sin tocar la base de datos
 *   Despues resuelve la relacion con Usuario buscando en la base
 *   Despues resuelve la relacion con Area buscando en la base
 *   Finalmente construye y persiste la entidad
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PermisoAccesoServiceImpl implements PermisoAccesoService {

    private final PermisoAccesoRepository permisoAccesoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final PermisoAccesoMapper permisoAccesoMapper;

    @Override
    public PermisoAccesoResponseDTO crear(PermisoAccesoRequestDTO request) {
        // Validacion semantica de fechas. Esta verificacion es la mas
        // barata de todas (solo compara dos valores en memoria), asi que
        // la hacemos primero para fallar lo mas rapido posible si las
        // fechas son inconsistentes.
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        PermisoAcceso entidad = permisoAccesoMapper.toEntity(request);
        entidad.setUsuario(usuario);
        entidad.setArea(area);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        PermisoAcceso guardado = permisoAccesoRepository.save(entidad);
        return permisoAccesoMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PermisoAccesoResponseDTO consultarPorId(Integer id) {
        PermisoAcceso entidad = permisoAccesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PermisoAcceso", id));
        return permisoAccesoMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermisoAccesoResponseDTO> listarTodos() {
        return permisoAccesoRepository.findAll()
                .stream()
                .map(permisoAccesoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermisoAccesoResponseDTO> listarHabilitados() {
        return permisoAccesoRepository.findAll()
                .stream()
                .filter(PermisoAcceso::getHabilitado)
                .map(permisoAccesoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PermisoAccesoResponseDTO actualizar(Integer id, PermisoAccesoRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        PermisoAcceso entidad = permisoAccesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PermisoAcceso", id));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        permisoAccesoMapper.updateEntity(entidad, request);
        entidad.setUsuario(usuario);
        entidad.setArea(area);

        PermisoAcceso actualizado = permisoAccesoRepository.save(entidad);
        return permisoAccesoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        PermisoAcceso entidad = permisoAccesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PermisoAcceso", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        permisoAccesoRepository.save(entidad);
    }

    /**
     * Metodo privado que centraliza la validacion de consistencia entre
     * las fechas de inicio y fin. Lo extraemos como metodo aparte para
     * reutilizarlo entre crear y actualizar, manteniendo el principio DRY
     * (no repetir codigo). Si la regla de validacion cambia en el futuro,
     * solo hay que modificar este metodo.
     *
     * Si la fecha de fin es igual o anterior a la de inicio, lanza
     * BusinessException que el manejador global convertira en HTTP 422.
     */
    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new BusinessException(
                    "La fecha de fin debe ser posterior a la fecha de inicio"
            );
        }
    }
}
