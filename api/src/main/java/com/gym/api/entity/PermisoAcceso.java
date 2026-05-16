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
 * Entidad que representa el permiso de un usuario para acceder a un area
 * durante un periodo de tiempo. Mapea la tabla Tbl_Permisos_Acceso del
 * diagrama.
 *
 * Esta entidad implementa el modelo de control de acceso del gimnasio.
 * Un mismo usuario puede tener varios permisos simultaneos a distintas
 * areas, y un mismo area puede tener permisos otorgados a muchos usuarios.
 * Las fechas de inicio y fin son Datetime, no Date, lo que permite definir
 * permisos con granularidad horaria (por ejemplo, acceso al area de
 * alberca solo durante horarios de tarde).
 *
 * Cada permiso tiene dos relaciones @ManyToOne: una hacia el usuario al
 * que se otorga el permiso, y otra hacia el area cuyo acceso se regula.
 * Ambas relaciones usan carga perezosa para control fino de consultas.
 */
@Entity
@Table(name = "Tbl_Permisos_Acceso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermisoAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_permiso_acceso")
    private Integer idPermisoAcceso;

    /**
     * Usuario al que se otorga el permiso de acceso.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Area cuyo acceso se esta otorgando.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_area", referencedColumnName = "Id_area", nullable = false)
    private Area area;

    /**
     * Momento exacto en que comienza la vigencia del permiso.
     */
    @Column(name = "Fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Momento exacto en que finaliza la vigencia del permiso.
     */
    @Column(name = "Fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
