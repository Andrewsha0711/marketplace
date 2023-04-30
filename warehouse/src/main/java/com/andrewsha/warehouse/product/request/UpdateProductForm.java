package com.andrewsha.warehouse.product.request;

import java.util.Map;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductForm {
    @Size(min = 3, max = 20, message = "product name must be greater than 3 but less than 20 characters")
    private String name;

    @Min(value = 1, message = "price must be greater than 1")
    private Double actualPrice;

    @Min(value = 0, message = "discount must be equal or greater than 0")
    @Max(value = 99, message = "discount must be equal or less than 99")
    private Double discount;

    private Map<String, String> attributes;
}
