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

import java.time.LocalDateTime;

/**
 * Entidad que representa los roles que puede tener un usuario en el sistema.
 * Mapea la tabla Cat_roles del diagrama.
 *
 * Ejemplos tipicos de roles: administrador, entrenador, recepcionista,
 * cliente. La tabla Tbl_Usuarios referencia este catalogo para determinar
 * el tipo de cada usuario y, en consecuencia, sus permisos en el sistema.
 */
@Entity
@Table(name = "Cat_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_rol")
    private Byte idRol;

    @Column(name = "Rol", nullable = false, length = 25)
    private String rol;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
