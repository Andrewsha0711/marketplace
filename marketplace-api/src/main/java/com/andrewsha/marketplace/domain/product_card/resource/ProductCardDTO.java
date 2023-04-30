package com.andrewsha.marketplace.domain.product_card.resource;

import com.andrewsha.marketplace.domain.product_card.ProductCard;
import com.toedter.spring.hateoas.jsonapi.JsonApiId;
import com.toedter.spring.hateoas.jsonapi.JsonApiType;
import lombok.Data;

@Data
public class ProductCardDTO {
    @JsonApiId
    private final String id;
    @JsonApiType
    private final String type = "product-card";
    private final String name;
    private final String shortDescription;
    private final String description;
    private final String category;

    public ProductCardDTO(ProductCard productCard) {
        this.id = productCard.getId();
        this.name = productCard.getName();
        this.shortDescription = productCard.getShortDescription();
        this.description = productCard.getDescription();
        this.category = productCard.getCategory();
    }
}
