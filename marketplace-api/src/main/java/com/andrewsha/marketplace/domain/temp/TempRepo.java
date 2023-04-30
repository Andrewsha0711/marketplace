package com.andrewsha.marketplace.domain.temp;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempRepo extends ElasticsearchRepository<TempObject, String>{
     
}
