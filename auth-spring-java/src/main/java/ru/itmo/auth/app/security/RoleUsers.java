package ru.itmo.auth.app.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.itmo.auth.app.security.UserPermission.*;

public enum RoleUsers {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(USER_READ, ADMIN_READ, ADMIN_WRITE));

    private final Set<UserPermission> permissions;

    RoleUsers(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> perms = getPermissions().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());
        perms.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return perms;
    }
}
