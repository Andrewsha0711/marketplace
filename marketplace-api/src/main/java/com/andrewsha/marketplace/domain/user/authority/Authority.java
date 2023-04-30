package com.andrewsha.marketplace.domain.user.authority;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Document(indexName = "authorities")
@Data
public class Authority {
	@Id
	private Long id;

	private String name;

	@JsonIgnore
	private Set<AuthoritySet> permissionSets = new HashSet<>();

	public Authority() {}

	public Authority(AuthorityEnum action, Class<?> targetObject) {
		this.name =
				action.toString().toUpperCase() + "_" + targetObject.getSimpleName().toUpperCase();
	}

	public String getAction() {
		return this.name.substring(0, this.name.indexOf("_"));
	}

	public String getTargetObjectType() {
		return (this.name.split("_"))[1];
	}
}
