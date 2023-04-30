package com.andrewsha.marketplace.domain.product_card;

import static com.toedter.spring.hateoas.jsonapi.MediaTypes.JSON_API_VALUE;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
import org.springframework.web.multipart.MultipartFile;
import com.andrewsha.marketplace.domain.product.Product;
import com.andrewsha.marketplace.domain.product_card.request.CreateProductCardForm;
import com.andrewsha.marketplace.domain.product_card.request.UpdateProductCardForm;
import com.andrewsha.marketplace.utils.DTOBuilder;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import com.toedter.spring.hateoas.jsonapi.MediaTypes;

@RestController
@RequestMapping(path = "api/v1/product-card")
@Validated
public class ProductCardController {
    @Autowired
    private ProductCardService productCardService;

    @Autowired
    private DTOBuilder<ProductCard> builder;

    @GetMapping(produces = JSON_API_VALUE)
    public ResponseEntity<?> getProductCards(
            @RequestParam(required = true) MultiValueMap<String, String> params)
            throws MissingServletRequestParameterException {
        return ResponseEntity
                .ok(this.builder.build(this.productCardService.getProductCards(params)));
    }

    @GetMapping(path = "/{id}", produces = JSON_API_VALUE)
    public ResponseEntity<?> getProductCard(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.builder.build(this.productCardService.getProductCard(id)));
    }

    @PostMapping(produces = JSON_API_VALUE)
    //TODO  @PreAuthorize("hasPermission(#productCard.store, 'ProductCard', 'CREATE')")
    public ResponseEntity<?> createProductCard(
            @Valid @RequestParam(value = "body", required = true) CreateProductCardForm productCard,
            @RequestParam(required = true) MultiValueMap<String, MultipartFile> files) {
        // return ResponseEntity.ok(
        // this.builder.build(this.productCardService.createProductCard(productCard, files)));
        return ResponseEntity.ok(this.productCardService.createProductCard(productCard, files));
    }

    @PatchMapping(path = "{productCardId}", produces = JSON_API_VALUE)
    @PreAuthorize("hasPermission(#id, 'ProductCard', 'UPDATE')")
    public ResponseEntity<?> patchProductCard(@PathVariable("productCardId") String id,
            @Valid @RequestBody UpdateProductCardForm productCardDetails) {
        return ResponseEntity.ok(this.builder
                .build(this.productCardService.patchProductCard(id, productCardDetails)));
    }

    @PutMapping(path = "{productCardId}", produces = JSON_API_VALUE)
    @PreAuthorize("hasPermission(#id, 'ProductCard', 'UPDATE')")
    public ResponseEntity<?> putProductCard(@PathVariable("productCardId") String id,
            @Valid @RequestBody UpdateProductCardForm productCardDetails) {
        return ResponseEntity.ok(
                this.builder.build(this.productCardService.putProductCard(id, productCardDetails)));
    }

    @PutMapping(path = "{productCardId}/products/")
    @PreAuthorize("hasPermission(#productCardId, 'ProductCard', 'UPDATE')")
    public ResponseEntity<?> putProductToProductCard(
            @PathVariable("productCardId") String productCardId,
            @Valid @RequestBody Product productDetails) {
        return ResponseEntity.ok(this.builder.build(
                this.productCardService.putProductToProductCard(productCardId, productDetails)));
    }

    @DeleteMapping(path = "{productCardId}", produces = MediaTypes.JSON_API_VALUE)
    //TODO @PreAuthorize("hasPermission(#id, 'ProductCard', 'DELETE')")
    public ResponseEntity<?> deleteProductCard(@PathVariable("productCardId") String id) {
        this.productCardService.deleteProductCard(id);
        //TODO null model
        return ResponseEntity.ok(JsonApiModelBuilder.jsonApiModel().meta("deleted", id));
    }
}
