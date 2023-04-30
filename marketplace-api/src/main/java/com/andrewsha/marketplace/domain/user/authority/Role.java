package com.andrewsha.marketplace.domain.user.authority;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.andrewsha.marketplace.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(indexName = "roles")
@Data
@AllArgsConstructor
public class Role {
    @Id
    private Long id;

    @NotNull
    // @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role(RoleEnum name) {
        this(null, name, null);
    }
}
