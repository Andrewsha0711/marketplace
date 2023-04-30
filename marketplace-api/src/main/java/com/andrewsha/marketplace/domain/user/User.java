package com.andrewsha.marketplace.domain.user;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.andrewsha.marketplace.config.Config;
import com.andrewsha.marketplace.domain.user.authority.Authority;
import com.andrewsha.marketplace.domain.user.authority.AuthoritySet;
import com.andrewsha.marketplace.domain.user.authority.Role;
import com.andrewsha.marketplace.domain.user.authority.RoleEnum;
import com.andrewsha.marketplace.security.authority.CustomGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User implements UserDetails {
    private static final long serialVersionUID = 4206343094785042075L;

    @Id
    private UUID id;

    @Pattern(regexp = Config.nameRegexp, message = "wrong name format")
    private String name;

    @Email(message = "cannot resolve to email address")
    private String email;

    @Pattern(regexp = Config.phoneNumberRegexp, message = "cannot resolve to phone number")
    private String phoneNumber;

    @JsonIgnore
    private String password;

    @Pattern(regexp = Config.imageUrlRegexp, message = "cannot resolve to image url")
    private String profileIcon;

    private LocalDate dob;

    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    private Set<AuthoritySet> authoritySets = new HashSet<>();

    private LocalDate accountExpired;

    private LocalDate accountLocked;

    private LocalDate credentialsExpired;

    // @Column(name = "disabled", nullable = true, updatable = true)
    // private LocalDate disabled;

    @AssertTrue(message = "email or phone number is required")
    private boolean isEmailOrPhoneNumberExists() {
        return this.email != null || this.phoneNumber != null;
    }

    @AssertTrue(message = "age must be greater than 14 and less than 110")
    private boolean isDateOfBirthValid() {
        long age = this.dob.until(LocalDate.now(), ChronoUnit.YEARS);
        if (age < 14 || age > 100) {
            return false;
        }
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<CustomGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.roles) {
            authorities.add(new CustomGrantedAuthority(role.getName()));
        }
        for (AuthoritySet authoritySet : this.authoritySets) {
            authorities.addAll(authoritySet.getAuthorities());
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        if (this.email != null) {
            return this.email;
        }
        if (this.phoneNumber != null) {
            return this.phoneNumber;
        }
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        if (this.accountExpired != null && this.accountExpired.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        if (this.accountLocked != null && this.accountLocked.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        if (this.credentialsExpired != null && this.credentialsExpired.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        // if(this.disabled != null && this.disabled.isBefore(LocalDate.now())) {
        // return false;
        // }
        return true;
    }

    @JsonIgnore
    public Integer getAge() {
        return (int) LocalDate.now().until(this.dob, ChronoUnit.YEARS);
    }

    public void addAuthority(Object authority) {
        if (authority instanceof Role) {
            ((Role) authority).getUsers().add(this);
            this.roles.add((Role) authority);
        }
        if (authority instanceof Authority) {
            // TODO complete
            // ((Permission) authority)
        }
    }

    public boolean hasRole(RoleEnum roleEnum) {
        for (Role role : this.roles) {
            if (role.getName().equals(roleEnum)) {
                return true;
            }
        }
        return false;
    }

    public void addAuthoritySet(AuthoritySet set) {
        this.authoritySets.add(set);
    }
}
