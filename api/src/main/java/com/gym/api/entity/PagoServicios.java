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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa el pago efectuado por una renta de servicios.
 * Mapea la tabla Tbl_Pago_Servicios del diagrama.
 *
 * Cierra el ciclo financiero del bloque de inventario: cada renta de
 * servicios puede tener uno o varios pagos asociados, y esta tabla
 * registra los detalles de cada cobro. Tiene una relacion hacia
 * RentaServicios (que renta se esta pagando) y otra hacia MetodoPago
 * (como se efectuo el cobro).
 *
 * El monto total se modela con BigDecimal porque representa una cantidad
 * de dinero, y los tipos de punto flotante (float, double) introducen
 * errores de redondeo inaceptables en calculos financieros.
 */
@Entity
@Table(name = "Tbl_Pago_Servicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoServicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_pago_servicio")
    private Integer idPagoServicio;

    /**
     * Renta de servicios que se esta pagando.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_renta_servicio", referencedColumnName = "Id_renta_servicio", nullable = false)
    private RentaServicios rentaServicios;

    /**
     * Monto total del pago. Se modela con BigDecimal y precision (10, 2)
     * para representar valores monetarios sin errores de redondeo.
     * Soporta valores hasta 99999999.99, mas que suficiente para cualquier
     * pago de servicios razonable.
     */
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    /**
     * Metodo de pago utilizado para efectuar el cobro.
     *
     * Notese que la columna en la base se llama Id_tipo_pago aunque
     * referencia al catalogo de metodos de pago. Esta es una decision del
     * diseyo original del diagrama que respetamos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_tipo_pago", referencedColumnName = "Id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
