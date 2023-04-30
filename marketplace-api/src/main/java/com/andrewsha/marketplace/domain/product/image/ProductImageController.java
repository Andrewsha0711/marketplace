package com.andrewsha.marketplace.domain.product.image;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.andrewsha.marketplace.domain.product.ProductService;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductImageController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductImageService productImagesService;

	@PostMapping(path = "{productId}/images")
	@PreAuthorize("hasPermission(#productId, 'Product', 'UPDATE')")
	public ResponseEntity<?> upload(@PathVariable("productId") String productId,
			@RequestParam(required = true) MultiValueMap<String, MultipartFile> files)
			throws IllegalStateException, IOException {
		return ResponseEntity.ok(
				this.productImagesService.upload(this.productService.getProduct(productId), files));
	}

	@PutMapping(path = "{productId}/images")
	@PreAuthorize("hasPermission(#productId, 'Product', 'UPDATE')")
	public ResponseEntity<?> putImage(@PathVariable("productId") String productId,
			@RequestBody ProductImageList productImageList) {
		return ResponseEntity
				.ok(this.productImagesService.putImageAsUrl(productId, productImageList));
	}

	@DeleteMapping(path = "{productId}/images/{imageId}")
	@PreAuthorize("hasPermission(#productId, 'Product', 'UPDATE')")
	public ResponseEntity<?> deleteProductImageById(@PathVariable("productId") String productId,
			@PathVariable("imageId") String imageId) {
		this.productImagesService.deleteProductImage(productId, imageId);
		return ResponseEntity
				.ok("product " + productId + ": image with id " + imageId + "successfully deleted");
	}
}
