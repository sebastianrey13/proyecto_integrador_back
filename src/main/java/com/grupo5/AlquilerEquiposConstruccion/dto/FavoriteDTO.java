package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavoriteDTO {

    private Integer id;
    private Product product;
    private User user;

    public FavoriteDTO(Product product, User user) {
        this.product = product;
        this.user = user;
    }
}
