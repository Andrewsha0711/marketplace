package com.andrewsha.marketplace.domain.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import com.andrewsha.marketplace.domain.product.image.ProductImage;
import com.andrewsha.marketplace.domain.product_card.ProductCard;
import lombok.Data;

@Document(indexName = "products")
@Data
public class Product {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    @NotBlank(message = "name cannot be empty")
    @Size(min = 3, max = 20,
            message = "product name must be greater than 3 but less than 20 characters")
    private String name;

    @Field(type = FieldType.Double)
    @NotNull(message = "price cannot be null")
    @Min(value = 1, message = "price must be greater than 1")
    private Double actualPrice;

    @Field(type = FieldType.Double)
    @Min(value = 0, message = "discount must be equal or greater than 0")
    @Max(value = 99, message = "discount must be equal or less than 99")
    private Double discount;

    @Valid
    @Field(type = FieldType.Nested)
    private List<ProductImage> images = new ArrayList<>();

    @Field(type = FieldType.Object)
    private Map<String, String> attributes = new HashMap<>();

    @Transient
    private Set<ProductCard> productCards = new HashSet<>();

    public Double getDiscountedPrice() {
        if (this.discount != null) {
            return this.actualPrice - this.actualPrice * (this.discount / 100);
        }
        return this.actualPrice;
    }

    public void setImages(List<ProductImage> images) {
        for (ProductImage productImage : images) {
            productImage.setProductId(this.id);
        }
        this.images = images;
    }

    public void addImage(ProductImage image) {
        this.images.add(image);
    }

    public void addImages(Iterable<? extends ProductImage> images) {
        images.forEach(e -> this.images.add(e));
    }
}
