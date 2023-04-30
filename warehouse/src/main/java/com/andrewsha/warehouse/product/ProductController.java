package com.andrewsha.warehouse.product;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrewsha.warehouse.product.request.CreateProductForm;
import com.andrewsha.warehouse.product.request.UpdateProductForm;
import com.andrewsha.warehouse.utils.DTOBuilder;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import com.toedter.spring.hateoas.jsonapi.MediaTypes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "api/v1/product")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DTOBuilder<Product> builder;

    @GetMapping(produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "page[number]", required = true) @Min(0) int page,
            @RequestParam(value = "page[size]", required = true) @Min(1) int size) {
        Page<Product> productsPage = this.productService.getProducts(page, size);
        return ResponseEntity.ok(this.builder.build(productsPage));
    }

    @GetMapping(path = "/{productId}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> getProduct(@PathVariable("productId") String id) {
        return ResponseEntity.ok(this.builder.build(this.productService.getProduct(id)));
    }

    @PostMapping(produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductForm product) {
        return ResponseEntity.ok(this.builder.build(this.productService.createProduct(product)));
    }

    @PatchMapping(path = "{productId}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String id,
            @Valid @RequestBody UpdateProductForm productDetails) {
        return ResponseEntity
                .ok(this.builder.build(this.productService.patchProduct(id, productDetails)));
    }

    @PutMapping(path = "{productId}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> putProduct(@PathVariable("productId") String id,
            @Valid @RequestBody UpdateProductForm productDetails) {
        return ResponseEntity
                .ok(this.builder.build(this.productService.putProduct(id, productDetails)));
    }

    @DeleteMapping(path = "{productId}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok(JsonApiModelBuilder.jsonApiModel().meta("deleted", id).build());
    }
}
