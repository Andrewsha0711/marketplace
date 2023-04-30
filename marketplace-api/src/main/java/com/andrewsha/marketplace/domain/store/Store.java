package com.andrewsha.marketplace.domain.store;

import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Data;

@Document(indexName = "stores")
@Data
public class Store {
    @Id
    private String id;

    @NotBlank(message = "store name cannot be empty or null")
    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    // private Set<ProductCard> productCards;
}
