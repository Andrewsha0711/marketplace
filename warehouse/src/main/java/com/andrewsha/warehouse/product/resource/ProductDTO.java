package com.andrewsha.warehouse.product.resource;

import java.util.Map;

import com.andrewsha.warehouse.product.Product;
import com.toedter.spring.hateoas.jsonapi.JsonApiId;
import com.toedter.spring.hateoas.jsonapi.JsonApiType;

import lombok.Data;

@Data
public class ProductDTO {
    @JsonApiId
    private final String id;
    @JsonApiType
    private final String type = "product";
    private final String name;
    private final String actualPrice;
    private final String discount;
    private final Map<String, String> productAttributes;

    public ProductDTO(Product product) {
        this.id = product.getId() != null ? product.getId() : null;
        this.name = product.getName();
        this.actualPrice =
                product.getActualPrice() != null ? product.getActualPrice().toString()
                        : null;
        this.discount =
                product.getDiscount() != null ? product.getDiscount().toString() : null;
        this.productAttributes = product.getAttributes();
    }
}
