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
 * Entidad que registra los comentarios de retroalimentacion que los
 * usuarios dejan sobre las clases. Mapea la tabla Tbl_Feedback del
 * diagrama.
 *
 * Cada feedback es un comentario de un usuario sobre una clase
 * especifica. Sirve para que el gimnasio recoja la opinion de sus
 * miembros, evalue la calidad de las clases, identifique problemas
 * recurrentes y mejore la experiencia general. Es una entidad
 * relativamente simple con solo dos relaciones y el texto del comentario.
 *
 * El campo Comentario es nullable porque es posible que el sistema
 * acepte feedbacks que solo contengan una valoracion numerica sin
 * texto adicional, aunque el diagrama actual no incluye esa valoracion
 * explicitamente. Si en el futuro se anyade una calificacion estrellada,
 * vivira como un campo nuevo en esta misma entidad.
 */
@Entity
@Table(name = "Tbl_Feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_feedback")
    private Integer idFeedback;

    /**
     * Usuario que dejo el comentario.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Clase sobre la que se deja el comentario.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_clase", referencedColumnName = "Id_clase", nullable = false)
    private Clase clase;

    /**
     * Texto del comentario. Opcional porque podria contener solo
     * valoracion sin texto adicional.
     */
    @Column(name = "Comentario", length = 300)
    private String comentario;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
