package com.andrewsha.marketplace.domain.product.image;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andrewsha.marketplace.domain.product.Product;
import com.andrewsha.marketplace.domain.product.ProductRepository;
import com.andrewsha.marketplace.exception.ProductServiceException;
import com.andrewsha.marketplace.storage.StorageService;

@Service
public class ProductImageService {
    @Autowired
    private StorageService storageService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Value("${api.endpoint.storage.products}")
    String endpoint;

    @Value("${upload.path.products}")
    String uploadPath;

    public Iterable<ProductImage> upload(Product product, Collection<MultipartFile> files) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(endpoint)
                .toUriString();
        Collection<String> paths =
                this.storageService.storeAll(files, this.uploadPath + "/" + product.getId());
        Collection<ProductImage> images = paths.stream()
                .map(e -> new ProductImage(product.getId(), baseUrl + product.getId() + "/" + e))
                .collect(Collectors.toList());
        return this.productImageRepository.saveAll(images);
    }

    @Transactional
    public Product upload(Product product, MultiValueMap<String, MultipartFile> files) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(endpoint)
                .toUriString();

        for (Map.Entry<String, List<MultipartFile>> entry : files.entrySet()) {
            Collection<String> paths = this.storageService.storeAll(entry.getValue(),
                    uploadPath + "/" + product.getId());
            List<ProductImage> images = paths.stream().map(e -> {
                String url = baseUrl + product.getId().toString() + "/" + e;
                return new ProductImage(product.getId(), url);
            }).collect(Collectors.toList());
            this.productImageRepository.saveAll(images);
            product.addImages(images);
        }
        // TODO bad way to change product
        return product;
    }

    public boolean deleteImageFromStorage(ProductImage productImage) {
        String imageName = productImage.getUrl().substring(productImage.getUrl().lastIndexOf("/"));
        File image = new File(uploadPath + "/" + productImage.getProductId() + imageName);
        if (image.exists()) {
            return image.delete();
        }
        return false;
    }

    @Transactional
    public boolean deleteProductImage(String productId, String imageId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException(
                        "product with id " + productId + " does not exists"));
        for (ProductImage productImage : product.getImages()) {
            if (productImage.getId().equals(imageId)) {
                this.productImageRepository.deleteById(imageId);
                return this.deleteImageFromStorage(productImage);
            }
        }
        throw new ProductServiceException("product with id " + productId + " : image with id "
                + imageId + " does not exists");
    }

    @Transactional
    public Product putImageAsUrl(String productId, ProductImageList productImageList) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException(
                        "product with id " + productId + " does not exists"));
        for (ProductImage productImage : productImageList.getProductImages()) {
            productImage.setProductId(product.getId());
            this.productImageRepository.save(productImage);
            product.addImages(List.of(productImage));
        }
        return product;
    }

    public Iterable<ProductImage> saveAll(Iterable<ProductImage> images) {
        return this.productImageRepository.saveAll(images);
    }
}
