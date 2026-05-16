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
 * Entidad que representa los dias de la semana usados en la programacion
 * de clases del gimnasio. Mapea la tabla Cat_dias del diagrama.
 *
 * Esta es una tabla de catalogo, lo que significa que su contenido es
 * relativamente estatico (los siete dias de la semana) y rara vez cambia.
 * Se usa como referencia desde la tabla Tbl_Clase_Dia para indicar en que
 * dia o dias de la semana se imparte una clase determinada.
 *
 * Notar que la clase se llama "Dia" en singular y sin prefijo, siguiendo
 * la convencion de nombrar las clases por el concepto del dominio que
 * representan. La conexion con el nombre de la tabla real "Cat_dias" se
 * declara explicitamente mediante la anotacion @Table.
 */
@Entity
@Table(name = "Cat_dias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dia {

    /**
     * Clave primaria de la tabla. Tipo Byte porque en MySQL la columna
     * es Tinyint, suficiente para albergar los siete dias de la semana.
     * La estrategia IDENTITY delega la generacion del valor al motor de
     * MySQL, que usara AUTO_INCREMENT automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_dia")
    private Byte idDia;

    /**
     * Nombre del dia de la semana, por ejemplo "Lunes" o "Martes".
     * Limitado a 10 caracteres porque ningun dia de la semana en espanol
     * supera ese tamayo. Se declara como nullable = false porque no tiene
     * sentido un registro de dia sin nombre.
     */
    @Column(name = "Dia", nullable = false, length = 10)
    private String dia;

    /**
     * Bandera de borrado logico. Cuando vale false, el registro se
     * considera dado de baja y debe ser filtrado por las consultas de
     * negocio. Permite preservar la integridad referencial cuando hay
     * otras tablas que referencian a este registro historicamente.
     */
    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    /**
     * Marca temporal del momento en que se creo el registro.
     * Se llena automaticamente en el alta y no deberia modificarse
     * despues. Sirve como informacion de auditoria.
     */
    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    /**
     * Marca temporal del momento en que se dio de baja logica el registro.
     * Es nullable porque solo tiene valor cuando el registro ha sido dado
     * de baja. Si esta vacio significa que el registro sigue activo.
     */
    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
