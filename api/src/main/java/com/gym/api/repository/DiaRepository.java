package com.gym.api.repository;

import com.gym.api.entity.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Dia. Proporciona acceso a la tabla Cat_dias.
 *
 * Al extender JpaRepository, esta interfaz hereda automaticamente todos
 * los metodos estandar de acceso a datos sin necesidad de implementar
 * ningun codigo. Spring Data JPA genera dinamicamente una implementacion
 * en tiempo de ejecucion y la registra como un bean en el contenedor de
 * Spring, listo para ser inyectado en servicios y otros componentes.
 *
 * Los dos parametros genericos son:
 *   - Dia: la entidad gestionada por este repositorio
 *   - Byte: el tipo de la clave primaria de Dia (Id_dia es Tinyint en
 *           MySQL, lo que en Java se mapea a Byte)
 *
 * Metodos heredados de JpaRepository disponibles automaticamente:
 *
 *   save(entidad)         guarda una nueva entidad o actualiza una existente
 *   findById(id)          busca por clave primaria, devuelve Optional
 *   findAll()             devuelve todas las entidades de la tabla
 *   findAll(Pageable)     devuelve una pagina de resultados
 *   findAll(Sort)         devuelve todas las entidades ordenadas
 *   deleteById(id)        elimina por clave primaria (borrado fisico)
 *   delete(entidad)       elimina una entidad
 *   count()               devuelve el numero total de filas
 *   existsById(id)        verifica si existe una entidad con ese id
 *
 * Si en el futuro se necesitan consultas personalizadas, se declaran en
 * esta misma interfaz siguiendo las convenciones de nombrado de Spring
 * Data o usando la anotacion @Query con JPQL.
 *
 * La anotacion @Repository es opcional cuando se extiende JpaRepository,
 * porque Spring detecta automaticamente los repositorios. Sin embargo,
 * declararla explicitamente hace el codigo mas claro para quien lo lee y
 * habilita ciertas optimizaciones de traduccion de excepciones de la
 * persistencia.
 */
@Repository
public interface DiaRepository extends JpaRepository<Dia, Byte> {
}
