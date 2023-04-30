package com.andrewsha.warehouse.warehouse;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import com.andrewsha.warehouse.product.Product;
import lombok.Data;

@Document(collection = "warehouses")
@Data
public class Warehouse {
    
    @Id
    private String id;

    @DocumentReference(collection = "products")
    private Map<Product, Integer> products;
}
