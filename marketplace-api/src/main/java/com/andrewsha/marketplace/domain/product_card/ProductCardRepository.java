package com.andrewsha.marketplace.domain.product_card;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductCardRepository extends ElasticsearchRepository<ProductCard, String> {
}
