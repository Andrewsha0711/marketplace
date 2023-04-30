package com.andrewsha.marketplace.domain.product;

import java.io.File;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.andrewsha.marketplace.config.Config;
import com.andrewsha.marketplace.domain.product.image.ProductImage;
import com.andrewsha.marketplace.domain.product.image.ProductImageService;
import com.andrewsha.marketplace.domain.product.request.CreateProductForm;
import com.andrewsha.marketplace.domain.product.request.UpdateProductForm;
import com.andrewsha.marketplace.exception.ProductServiceException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    @Autowired
    private Logger logger;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageService imageService;

    @Value("${upload.path.products}")
    String uploadPath;

    public Page<Product> getProducts(int page, int size) {
        // TODO check get
        return this.productRepository.findAll(PageRequest.of(page, size));
    }

    public Product getProduct(String id) {
        return this.productRepository.findById(id).orElseThrow(
                () -> new ProductServiceException("product with id " + id + " does not exists"));
    }

    @Transactional
    public Product createProduct(CreateProductForm form,
            MultiValueMap<String, MultipartFile> files) {
        Product product = new Product();
        product.setName(form.getName());
        product.setActualPrice(form.getActualPrice());
        product.setDiscount(form.getDiscount());
        // product.setAttributes(form.getAttributes());

        // generated id
        String id = this.productRepository.save(product).getId();

        this.imageService.saveAll(form.getImages().stream()
                .map(e -> new ProductImage(id, e)).collect(Collectors.toList()));

        // form.getImages().forEach(i -> product.addImage(new ProductImage(product.getId(), i)));
        Collection<MultipartFile> relatedFiles = form.getUploaded().stream().map(e -> files.get(e))
                .filter(Objects::nonNull).flatMap(e -> e.stream()).collect(Collectors.toList());

        // product.addImages(this.imageService.upload(product, relatedFiles));
        return product;
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
        if (productDetails.getImages() != null) {
            if (!productDetails.getImages().isEmpty()) {
                product.setImages(productDetails.getImages());
            }
        }
        if (productDetails.getAttributes() != null) {
            if (!productDetails.getAttributes().isEmpty()) {
                // product.setAttributes(productDetails.getAttributes());
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
        product.setImages(productDetails.getImages());
        // product.setAttributes(productDetails.getAttributes());
        return this.productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductServiceException("product with id " + id + " does not exists"));
        // product.deleteProductCards();
        File storageFolder = new File(uploadPath + "/" + id);
        if (storageFolder.exists()) {
            storageFolder.delete();
        }
        this.productRepository.deleteById(id);
    }
}
