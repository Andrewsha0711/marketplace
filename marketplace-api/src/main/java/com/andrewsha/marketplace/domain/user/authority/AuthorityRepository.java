package com.andrewsha.marketplace.domain.user.authority;

import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends ElasticsearchRepository<Authority, Long> {
	public Optional<Authority> findByName(String name);

	public boolean existsByName(String name);
}
