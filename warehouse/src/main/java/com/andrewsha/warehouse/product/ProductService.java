package com.andrewsha.warehouse.product;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrewsha.warehouse.exception.ProductServiceException;
import com.andrewsha.warehouse.product.request.CreateProductForm;
import com.andrewsha.warehouse.product.request.UpdateProductForm;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(int page, int size) {
        // TODO check get
        return this.productRepository.findAll(PageRequest.of(page, size));
    }

    public Product getProduct(String id) {
        return this.productRepository.findById(id).orElseThrow(
                () -> new ProductServiceException("product with id " + id + " does not exists"));
    }

    @Transactional
    public Product createProduct(CreateProductForm form) {
        Product product = new Product();
        product.setName(form.getName());
        product.setActualPrice(form.getActualPrice());
        product.setDiscount(form.getDiscount());
        product.setAttributes(form.getAttributes());
        return this.productRepository.save(product);
    }

    @Transactional
    public Product patchProduct(String id, UpdateProductForm productDetails) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductServiceException("product with id " + id + " does not exists"));
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getActualPrice() != null) {
            product.setActualPrice(productDetails.getActualPrice());
        }
        if (productDetails.getDiscount() != null) {
            product.setDiscount(productDetails.getDiscount());
        }
        if (productDetails.getAttributes() != null) {
            if (!productDetails.getAttributes().isEmpty()) {
                product.setAttributes(productDetails.getAttributes());
            }
        }
        return this.productRepository.save(product);
    }

    @Transactional
    public Product putProduct(String id, UpdateProductForm productDetails) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductServiceException("product with id " + id + " does not exists"));

        product.setName(productDetails.getName());
        product.setActualPrice(productDetails.getActualPrice());
        product.setDiscount(productDetails.getDiscount());
        product.setAttributes(productDetails.getAttributes());
        return this.productRepository.save(product);
    }

    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }
}
