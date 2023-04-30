package com.andrewsha.marketplace.domain.product_card;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import com.andrewsha.marketplace.domain.product.Product;
import com.andrewsha.marketplace.domain.store.Store;
import com.andrewsha.marketplace.security.IBinaryTree;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Document(indexName = "product_cards")
@Data
public class ProductCard implements Serializable, IBinaryTree {
	private static final long serialVersionUID = -521228307643576282L;
	@Id
	private String id;

    @Field(type = FieldType.Keyword)
	@NotBlank(message = "name cannot be empty or null")
	@Size(min = 3, max = 20,
			message = "product name must be greater than 3 but less than 20 characters")
	private String name;

    @Field(type = FieldType.Keyword)
	@Size(min = 10, max = 50,
			message = "short description must be greater than 10 but less than 50 characters")
	private String shortDescription;

    @Field(type = FieldType.Text)
	@Size(min = 10, max = 100,
			message = "description must be greater than 10 but less than 100 characters")
	private String description;

	@NotEmpty(message = "set of products cannot be empty")
    @Field(type = FieldType.Nested, includeInParent = true)
	private Set<Product> products = new HashSet<>();

    @Field(type = FieldType.Text)
	@NotBlank(message = "category cannot be empty or null")
	private String category;

	@NotNull(message = "store cannot be null")
    @Field(type = FieldType.Nested, includeInParent = true)
	private Store store;

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public void deleteProduct(Product product) {
		this.products.remove(product);
		product.getProductCards().remove(this);
	}

	public void deleteProducts() {
		for (Product product : this.products) {
			product.getProductCards().remove(this);
		}
		this.products.clear();
	}

	@JsonIgnore
	@Override
	public SimpleEntry<String, Serializable> getRoot() {
		return new SimpleEntry<>("GLOBAL", null);
	}

	@JsonIgnore
	@Override
	public SimpleEntry<String, Serializable> getParent() {
		return new SimpleEntry<>(Store.class.getSimpleName(), this.store.getId());
	}
}
