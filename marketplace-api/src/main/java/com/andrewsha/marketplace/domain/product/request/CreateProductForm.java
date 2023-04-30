package com.andrewsha.marketplace.domain.product.request;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProductForm {
    @Size(min = 3, max = 20,
            message = "product name must be greater than 3 but less than 20 characters")
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Min(value = 1, message = "price must be greater than 1")
    @NotNull(message = "actual price cannot be null")
    private Double actualPrice;

    @Min(value = 0, message = "discount must be equal or greater than 0")
    @Max(value = 99, message = "discount must be equal or less than 99")
    private Double discount;

    private List<@Pattern(
            regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "wrong image url") String> images;
    private List<String> uploaded;

    @AssertTrue(message = "set of images (attached files or specified url) cannot be empty")
    public boolean isImageUrlOrUploadedFilesSpecified() {
        return (this.images.size() + this.uploaded.size() > 0);
    }

    private Map<String, String> attributes;
}
