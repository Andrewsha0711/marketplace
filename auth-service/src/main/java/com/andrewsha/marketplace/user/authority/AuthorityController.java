package com.andrewsha.marketplace.user.authority;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.andrewsha.marketplace.user.User;
import com.andrewsha.marketplace.user.UserService;
import com.andrewsha.marketplace.user.request.AddAuthorityForm;
import com.andrewsha.marketplace.utils.DTOBuilder;
import com.toedter.spring.hateoas.jsonapi.MediaTypes;

@RestController
@RequestMapping(path = "api/v1/user")
@Validated
public class AuthorityController {

	@Autowired
	private UserService userService;

	@Autowired
	private DTOBuilder<User> builder;

	@PutMapping(path = "{userId}/roles/{roleId}", produces = MediaTypes.JSON_API_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> addRole(@PathVariable("userId") UUID userId,
			@PathVariable("roleId") Long roleId, Authentication authentication) {
		return ResponseEntity
				.ok(this.builder.build(this.userService.addRole(userId, roleId, authentication)));
	}

	@PutMapping(path = "{userId}/authorities", produces = MediaTypes.JSON_API_VALUE)
	public ResponseEntity<?> addAuthority(@PathVariable("userId") UUID userId,
			@Valid @RequestBody AddAuthorityForm form) {
		return ResponseEntity.ok(this.builder.build(this.userService.addPermission(userId, form)));
	}
}
