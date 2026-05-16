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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa los tipos de membresia ofrecidos en el gimnasio.
 * Mapea la tabla Cat_membresias del diagrama.
 *
 * A diferencia de los otros catalogos del sistema, esta entidad tiene mas
 * atributos porque cada tipo de membresia define no solo su nombre sino
 * tambien su precio, su duracion en dias, y si incluye o no acceso a
 * clases grupales. La tabla Tbl_Inscripciones referencia este catalogo
 * para indicar a que tipo de membresia se inscribio cada usuario.
 *
 * Notar que el precio se modela con BigDecimal y no con float o double.
 * Esto es una buena practica obligatoria al trabajar con dinero, porque
 * los tipos de punto flotante introducen errores de redondeo inaceptables
 * en calculos financieros. BigDecimal preserva la precision decimal exacta.
 *
 * El tipo de Id_membresia es Short porque el diagrama lo declara como
 * Smallint, anticipando mas variantes posibles que en otros catalogos
 * pero sin esperar volumenes masivos.
 */
@Entity
@Table(name = "Cat_membresias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_membresia")
    private Short idMembresia;

    @Column(name = "Membresia", nullable = false, length = 100)
    private String membresia;

    @Column(name = "Descripcion", length = 250)
    private String descripcion;

    @Column(name = "Precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "Duracion_dias", nullable = false)
    private Short duracionDias;

    @Column(name = "Incluye_clases", nullable = false)
    private Boolean incluyeClases;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
