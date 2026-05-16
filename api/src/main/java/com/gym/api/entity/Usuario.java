package com.gym.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa una cuenta de acceso al sistema.
 * Mapea la tabla Tbl_Usuarios del diagrama.
 *
 * Un usuario es una cuenta con credenciales (nombre de usuario y
 * contraseña) que pertenece a una persona y tiene un rol asociado que
 * determina sus permisos en el sistema. La separacion entre Usuario y
 * Persona permite que existan personas sin cuenta (por ejemplo, menores
 * inscritos por sus padres) y, en teoria, que una misma persona tenga
 * varias cuentas con roles distintos, aunque eso sea poco comun.
 *
 * Esta entidad tiene dos relaciones @ManyToOne: una hacia Persona, que
 * contiene los datos personales, y otra hacia Rol, que define los
 * permisos del usuario. Ambas relaciones usan carga perezosa (LAZY) para
 * tener control fino sobre cuando se consultan los datos relacionados.
 *
 * SEGURIDAD: la contrasenya jamas debe almacenarse en texto plano. Cuando
 * se implemente el servicio de registro de usuarios, debe aplicarse un
 * algoritmo de hash criptografico como BCrypt antes de guardar el valor
 * en este campo. Aqui se modela simplemente como String porque el hash
 * tambien es una cadena de texto.
 */
@Entity
@Table(name = "Tbl_Usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_usuario")
    private Integer idUsuario;

    @Column(name = "Usuario", nullable = false, length = 50)
    private String usuario;

    /**
     * Contrasenya del usuario, almacenada como hash criptografico, nunca
     * en texto plano. El nombre de la columna se simplifica a "password"
     * en lugar del "Contrasenya" original del diagrama para evitar
     * problemas con caracteres especiales en algunos entornos.
     *
     * La longitud de 200 caracteres es generosa y permite albergar hashes
     * de diferentes algoritmos sin quedarse corto.
     */
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    /**
     * Relacion hacia la persona a la que pertenece esta cuenta.
     *
     * @ManyToOne porque varios usuarios podrian pertenecer a la misma
     * persona (caso teorico, normalmente sera uno a uno). FetchType.LAZY
     * para que la persona se cargue solo cuando se accede a ella
     * explicitamente, lo cual da control fino sobre las consultas.
     *
     * @JoinColumn especifica que la columna fisica de la base que actua
     * como clave foranea se llama Id_Persona, y referencia a Id_persona
     * de la tabla Tbl_Personas. nullable = false porque todo usuario
     * debe estar asociado a una persona.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Persona", referencedColumnName = "Id_persona", nullable = false)
    private Persona persona;

    /**
     * Relacion hacia el rol que define los permisos de este usuario.
     *
     * Misma logica que la relacion con Persona: @ManyToOne porque varios
     * usuarios pueden compartir el mismo rol, FetchType.LAZY para control
     * de consultas, y @JoinColumn declarando explicitamente la columna
     * de clave foranea en la tabla Tbl_Usuarios.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_rol", referencedColumnName = "Id_rol", nullable = false)
    private Rol rol;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
