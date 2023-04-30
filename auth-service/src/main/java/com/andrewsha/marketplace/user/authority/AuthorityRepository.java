package com.andrewsha.marketplace.user.authority;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	public Optional<Authority> findByName(String name);

	public boolean existsByName(String name);
}
