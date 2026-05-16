package com.gym.api.repository;

import com.gym.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de la entidad Usuario.
 *
 * Esta es la primera vez en el proyecto que añadimos metodos personalizados
 * a un repositorio. Hasta ahora todos los repositorios estaban vacios,
 * heredando solamente las operaciones estandar de JpaRepository. Para
 * Usuario necesitamos dos operaciones adicionales relacionadas con la
 * unicidad del nombre de cuenta.
 *
 * Estos metodos se llaman "query methods" o metodos derivados, y son una
 * de las caracteristicas mas elegantes de Spring Data JPA. La idea es que
 * tu declaras el metodo siguiendo una convencion de nombrado especifica, y
 * Spring Data lee el nombre, lo interpreta, y genera automaticamente la
 * consulta SQL correspondiente sin que tu escribas una sola palabra de SQL.
 *
 * Las palabras clave de la convencion que estamos usando aqui:
 *
 *   "exists" indica que el metodo verifica existencia y devuelve boolean
 *   "find" indica que el metodo busca y devuelve la entidad o un Optional
 *   "By" introduce la clausula de filtrado
 *   El nombre que sigue a "By" corresponde al atributo de la entidad por
 *   el cual se filtra
 *
 * En el caso de existsByUsuario, Spring genera una consulta equivalente a
 * "SELECT COUNT(*) > 0 FROM Tbl_Usuarios WHERE Usuario = ?". En el caso
 * de findByUsuario, equivale a "SELECT * FROM Tbl_Usuarios WHERE Usuario = ?".
 *
 * findByUsuario devuelve Optional porque la busqueda puede no encontrar
 * resultados. Esto es coherente con el patron que ya hemos visto en
 * findById, y obliga al codigo que consume el repositorio a manejar
 * explicitamente el caso de ausencia.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Verifica si existe un usuario con el nombre de cuenta especificado.
     * Util para validar la unicidad antes de crear un nuevo usuario.
     */
    boolean existsByUsuario(String usuario);

    /**
     * Busca un usuario por su nombre de cuenta. Devuelve Optional vacio si
     * no se encuentra. Sera util cuando implementemos autenticacion para
     * recuperar el usuario que esta intentando iniciar sesion.
     */
    Optional<Usuario> findByUsuario(String usuario);
}
