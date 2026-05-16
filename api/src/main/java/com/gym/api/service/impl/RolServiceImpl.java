package com.gym.api.service.impl;

import com.gym.api.dto.request.RolRequestDTO;
import com.gym.api.dto.response.RolResponseDTO;
import com.gym.api.entity.Rol;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.repository.RolRepository;
import com.gym.api.service.RolService;
import com.gym.api.util.RolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion concreta del contrato definido en RolService.
 *
 * Esta clase contiene la logica real de las operaciones de negocio
 * relacionadas con roles. Es responsable de coordinar el flujo entre
 * las peticiones del cliente y la base de datos, aplicando las reglas
 * de negocio en el camino.
 *
 * Anotaciones importantes:
 *
 * @Service registra esta clase como un bean de Spring de la capa de
 * servicios. Spring la detectara durante el escaneo y la tendra lista
 * para inyectar en los controladores que la necesiten.
 *
 * @RequiredArgsConstructor (de Lombok) genera automaticamente un
 * constructor con todos los campos marcados como 'final'. Este patron de
 * inyeccion de dependencias por constructor es el recomendado por Spring
 * porque hace que las dependencias sean inmutables y obligatorias,
 * facilitando la testeabilidad y el razonamiento sobre el codigo.
 *
 * @Transactional a nivel de clase indica que todos los metodos publicos
 * se ejecutaran dentro de una transaccion. Esto significa que si algo
 * falla a la mitad de una operacion, todos los cambios se revierten
 * automaticamente, manteniendo la base de datos en un estado consistente.
 * Para los metodos de solo lectura, podemos sobreescribir con
 * @Transactional(readOnly = true) para que Hibernate aplique optimizaciones.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    /**
     * Crea un nuevo rol. El sistema asigna automaticamente:
     *   - habilitado en true (todo rol nuevo nace activo)
     *   - fechaAlta con el momento actual
     *   - fechaBaja en null (no esta dado de baja)
     */
    @Override
    public RolResponseDTO crear(RolRequestDTO request) {
        Rol entidad = rolMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Rol guardado = rolRepository.save(entidad);
        return rolMapper.toResponseDTO(guardado);
    }

    /**
     * Consulta un rol por su ID. Si no existe, lanza una excepcion que
     * el GlobalExceptionHandler convertira en una respuesta HTTP 404.
     *
     * El metodo orElseThrow del Optional es una forma elegante de manejar
     * el caso en que la busqueda no encuentre resultados: si el Optional
     * esta vacio, ejecuta la funcion provista para lanzar la excepcion.
     */
    @Override
    @Transactional(readOnly = true)
    public RolResponseDTO consultarPorId(Byte id) {
        Rol entidad = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));
        return rolMapper.toResponseDTO(entidad);
    }

    /**
     * Devuelve la lista completa de roles. Util para la gestion
     * administrativa donde se necesita ver tambien los registros
     * historicos o dados de baja.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDTO> listarTodos() {
        return rolRepository.findAll()
                .stream()
                .map(rolMapper::toResponseDTO)
                .toList();
    }

    /**
     * Devuelve solo los roles que estan activos. Este es el metodo que se
     * usara en la mayoria de los casos de negocio donde solo interesan
     * los registros vigentes.
     *
     * Esta version filtra en memoria. Para volumenes grandes seria mejor
     * filtrar en base de datos con un metodo derivado en el repositorio
     * como findByHabilitadoTrue, pero para catalogos con pocos registros
     * el filtrado en memoria es perfectamente aceptable.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDTO> listarHabilitados() {
        return rolRepository.findAll()
                .stream()
                .filter(Rol::getHabilitado)
                .map(rolMapper::toResponseDTO)
                .toList();
    }

    /**
     * Actualiza un rol existente. Solo modifica los campos que el cliente
     * puede cambiar, dejando intactas las fechas de auditoria. El metodo
     * save de JPA detecta que la entidad ya tiene ID y genera un UPDATE
     * en lugar de un INSERT.
     */
    @Override
    public RolResponseDTO actualizar(Byte id, RolRequestDTO request) {
        Rol entidad = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));

        rolMapper.updateEntity(entidad, request);

        Rol actualizado = rolRepository.save(entidad);
        return rolMapper.toResponseDTO(actualizado);
    }

    /**
     * Elimina logicamente un rol. En lugar de borrar el registro de la
     * base de datos, lo marca como deshabilitado y registra la fecha de
     * baja. Esto preserva la integridad referencial con las tablas que
     * referencian este rol y permite recuperar informacion historica.
     *
     * Si en algun momento se necesita hacer borrado fisico, deberia ser
     * una operacion especial y deliberada, no parte del flujo normal de
     * eliminacion.
     */
    @Override
    public void eliminar(Byte id) {
        Rol entidad = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        rolRepository.save(entidad);
    }
}
