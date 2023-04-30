package com.andrewsha.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.andrewsha.warehouse.product.Product;
import com.andrewsha.warehouse.product.resource.ProductDTOBuilder;
import com.andrewsha.warehouse.utils.DTOBuilder;
import com.andrewsha.warehouse.warehouse.Warehouse;
import com.andrewsha.warehouse.warehouse.resource.WarehouseDTOBuilder;

@Configuration
public class AppBeans {

    @Bean
    public DTOBuilder<Product> productDtoBuilder() {
        return new ProductDTOBuilder();
    }

    @Bean
    public DTOBuilder<Warehouse> warehouseDtoBuilder() {
        return new WarehouseDTOBuilder();
    }
}
