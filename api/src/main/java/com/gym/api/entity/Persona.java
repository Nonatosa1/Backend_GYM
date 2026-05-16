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
 * Entidad que representa a una persona registrada en el sistema.
 * Mapea la tabla Tbl_Personas del diagrama.
 *
 * Esta entidad agrupa los datos personales de cualquier individuo que
 * tenga presencia en el sistema, independientemente de si esa persona
 * tiene o no una cuenta de acceso. La separacion entre Persona y Usuario
 * permite registrar personas sin cuenta (por ejemplo, menores inscritos
 * por sus padres) y deja una arquitectura mas limpia para aplicar
 * politicas de privacidad sobre los datos personales.
 *
 * La relacion con Usuario se modela desde el lado de Usuario, que tiene
 * una referencia @ManyToOne hacia Persona. Aqui no declaramos el lado
 * inverso de la relacion para mantener la entidad simple, aunque en el
 * futuro podriamos anyadir un @OneToOne si lo necesitamos.
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
    @Column(name = "Id_persona")
    private Integer idPersona;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Primer_apellido", nullable = false, length = 100)
    private String primerApellido;

    /**
     * Segundo apellido. Es opcional porque no todas las personas tienen
     * segundo apellido o lo declaran, especialmente en contextos
     * internacionales o con personas de un solo apellido.
     */
    @Column(name = "Segundo_apellido", length = 100)
    private String segundoApellido;

    /**
     * Fecha de nacimiento. Se modela con LocalDate (no LocalDateTime)
     * porque las fechas de nacimiento no necesitan precision horaria.
     * Usar el tipo correcto documenta la intencion del campo y evita
     * confusiones con conversiones de zona horaria.
     */
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
}
