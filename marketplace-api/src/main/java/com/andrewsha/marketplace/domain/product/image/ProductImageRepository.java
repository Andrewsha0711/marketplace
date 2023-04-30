package com.andrewsha.marketplace.domain.product.image;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductImageRepository extends ElasticsearchRepository<ProductImage, String> {
}
