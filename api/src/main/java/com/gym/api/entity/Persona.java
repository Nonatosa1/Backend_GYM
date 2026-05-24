package com.gym.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Persona.
 *
 * Representa a una persona registrada en el sistema. Contiene los datos
 * basicos de identidad, los campos de auditoria estandar, y los campos
 * relacionados con la confirmacion de cuenta por correo electronico.
 *
 * El campo tokenConfirmacion almacena un UUID generado al momento del
 * registro, que sirve como llave secreta para que la persona active su
 * cuenta haciendo clic en el enlace recibido por correo. Una vez usado,
 * el token se elimina (se pone a null) para evitar reutilizaciones.
 *
 * El campo cuentaConfirmada indica si la persona ya paso por el proceso
 * de confirmacion. Es importante notar que cuentaConfirmada es distinto
 * de habilitado: una persona puede estar confirmada pero deshabilitada
 * administrativamente, o habilitada pero no confirmada (estado intermedio
 * inmediatamente despues del registro).
 */
@Entity
@Table(name = "Tbl_Personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Persona")
    private Integer idPersona;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Primer_apellido", nullable = false, length = 100)
    private String primerApellido;

    @Column(name = "Segundo_apellido", length = 100)
    private String segundoApellido;

    @Column(name = "Fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "Correo", nullable = false, length = 150)
    private String correo;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;

    /**
     * Token UUID generado al registrar la persona. Se incluye en el enlace
     * de confirmacion enviado por correo. Una vez confirmada la cuenta,
     * este campo se pone a null para que el token no pueda reutilizarse.
     */
    @Column(name = "Token_confirmacion", length = 36)
    private String tokenConfirmacion;

    /**
     * Indica si la persona confirmo su cuenta haciendo clic en el enlace
     * del correo. Al crear una persona, este campo se inicializa en false.
     * Cambia a true cuando la persona accede al endpoint de confirmacion
     * con un token valido.
     */
    @Column(name = "Cuenta_confirmada", nullable = false)
    private Boolean cuentaConfirmada;
}
