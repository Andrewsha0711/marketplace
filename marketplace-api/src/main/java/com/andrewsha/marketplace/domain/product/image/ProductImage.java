package com.andrewsha.marketplace.domain.product.image;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.andrewsha.marketplace.config.Config;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(indexName = "product_images")
@Data
@AllArgsConstructor
public class ProductImage {
    @Id
    private String id;
    private String productId;

    @NotBlank(message = "image url cannot be empty or null")
    @Pattern(regexp = Config.imageUrlRegexp, message = "cannot resolve to image url")
    private String url;

    public ProductImage(String productId, String url) {
        this(null, productId, url);
    }
}
