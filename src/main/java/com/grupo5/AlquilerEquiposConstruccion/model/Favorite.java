package com.grupo5.AlquilerEquiposConstruccion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="favorites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Favorite(Product product, User user) {
        this.product = product;
        this.user = user;
    }
}
