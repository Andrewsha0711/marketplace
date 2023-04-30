package com.andrewsha.warehouse.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import com.andrewsha.warehouse.warehouse.Warehouse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Document(collection = "products")
@Data
public class Product {
	@Id
	private String id;

	@NotBlank(message = "name cannot be empty")
	@Size(min = 3, max = 20,
			message = "product name must be greater than 3 but less than 20 characters")
	private String name;

	@NotNull(message = "price cannot be null")
	@Min(value = 1, message = "price must be greater than 1")
	private Double actualPrice;

	@Min(value = 0, message = "discount must be equal or greater than 0")
	@Max(value = 99, message = "discount must be equal or less than 99")
	private Double discount;

	private Map<String, String> attributes = new HashMap<>();
	
    @DocumentReference(collection = "warehouses")
    private Map<Warehouse, Integer> warehouses = new HashMap<>();
}
