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
 * Entidad que representa los metodos de pago aceptados en el sistema.
 * Mapea la tabla Cat_metodo_pago del diagrama.
 *
 * Ejemplos tipicos: efectivo, tarjeta de credito, tarjeta de debito,
 * transferencia bancaria, pago en linea. La tabla Tbl_Detalles_Pagos
 * referencia este catalogo para registrar como se efectuo cada cobro.
 */
@Entity
@Table(name = "Cat_metodo_pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_metodo_pago")
    private Byte idMetodoPago;

    @Column(name = "Metodo_pago", nullable = false, length = 100)
    private String metodoPago;

    @Column(name = "Descripcion", length = 200)
    private String descripcion;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
