package com.andrewsha.marketplace.domain.product_card.request;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.andrewsha.marketplace.domain.product.request.CreateProductForm;
import lombok.Data;

@Data
public class CreateProductCardForm {
    @NotBlank(message = "name cannot be empty or null")
    @Size(min = 3, max = 20,
            message = "product name must be greater than 3 but less than 20 characters")
    private String name;

    @Size(min = 10, max = 50,
            message = "short description must be greater than 10 but less than 50 characters")
    private String shortDescription;

    @Size(min = 10, max = 100,
            message = "description must be greater than 10 but less than 100 characters")
    private String description;

    @Valid
    @NotEmpty(message = "set of products cannot be empty")
    private Set<CreateProductForm> products = new HashSet<>();

    @NotBlank(message = "category cannot be empty or null")
    private String category;

    @NotNull(message = "store id cannot be null")
    private String store;
}
