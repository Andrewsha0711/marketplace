package com.andrewsha.marketplace.domain.user.authority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.andrewsha.marketplace.domain.user.User;
import com.andrewsha.marketplace.security.authority.CustomGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Document(indexName = "authority-sets")
@Data
public class AuthoritySet {
	@Id
	private UUID id;

	private String scope;

	private Set<User> users = new HashSet<>();

	private Set<Authority> authoritySet = new HashSet<>();

	@JsonIgnore
	public Collection<CustomGrantedAuthority> getAuthorities() {
		Collection<CustomGrantedAuthority> authorities = new ArrayList<>();
		for (Authority auth : this.authoritySet) {
			authorities.add(new CustomGrantedAuthority(auth, this.scope));
		}
		return authorities;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public void addAuthorities(Set<Authority> set) {
		this.authoritySet.addAll(set);
	}

	public void setAuthoritySet(Set<Authority> authoritySet) {
		this.authoritySet = authoritySet;
	}
}
