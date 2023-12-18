package com.grupo5.AlquilerEquiposConstruccion.utils;

import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public class ProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateProductFromDto(ProductDTO product, @MappingTarget Product entity) {

    }
}
