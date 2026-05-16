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
 * Entidad que registra la informacion medica relevante de un usuario.
 * Mapea la tabla Tbl_Informacion_Medica del diagrama.
 *
 * Esta entidad captura datos sanitarios necesarios para emergencias y
 * para personalizar el servicio: tipo de sangre, alergias conocidas,
 * condiciones de salud relevantes y telefono de emergencia. Es
 * informacion sensible que debe protegerse adecuadamente y a la que solo
 * deberian tener acceso los roles autorizados (administradores y
 * personal medico, no otros usuarios).
 *
 * IMPORTANTE: cuando se implementen los controladores y los DTOs, esta
 * entidad NO debe exponerse libremente en respuestas publicas de la API.
 * Habra que filtrar quien puede consultarla y posiblemente encriptar
 * algunos campos a nivel de base de datos en el futuro.
 *
 * Aunque conceptualmente cada usuario tiene un solo registro de
 * informacion medica, la relacion se modela como @ManyToOne porque la
 * estructura del diagrama lo permite y nos da flexibilidad para casos
 * historicos (por ejemplo, mantener informacion medica anterior si el
 * usuario actualiza sus datos).
 */
@Entity
@Table(name = "Tbl_Informacion_Medica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InformacionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_informacion_medica")
    private Integer idInformacionMedica;

    /**
     * Usuario al que pertenece esta informacion medica.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Tipo de sangre del usuario. Formato corto como "O+", "AB-", "A+".
     */
    @Column(name = "Tipo_sangre", nullable = false, length = 5)
    private String tipoSangre;

    /**
     * Alergias conocidas del usuario. Campo de texto extenso porque
     * puede contener varias alergias con descripciones detalladas.
     */
    @Column(name = "Alergias", length = 500)
    private String alergias;

    /**
     * Condiciones de salud relevantes (hipertension, diabetes, lesiones
     * previas, etc.). Campo extenso para permitir descripciones
     * completas.
     */
    @Column(name = "Condiciones_de_salud", length = 500)
    private String condicionesDeSalud;

    /**
     * Numero telefonico para contactar en caso de emergencia. Longitud
     * de 15 caracteres para acomodar prefijos internacionales.
     */
    @Column(name = "Telefono_emergencia", nullable = false, length = 15)
    private String telefonoEmergencia;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
