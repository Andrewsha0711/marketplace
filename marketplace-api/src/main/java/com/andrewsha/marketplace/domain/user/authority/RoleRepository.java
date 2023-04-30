package com.andrewsha.marketplace.domain.user.authority;

import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RoleRepository extends ElasticsearchRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

    public boolean existsByName(RoleEnum name);
}
