package dev.cleysonph.smartgym.core.services.auth;

import dev.cleysonph.smartgym.core.enums.Role;
import dev.cleysonph.smartgym.core.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticatedUserTest {

    private User user;
    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(UUID.randomUUID())
            .email("test@example.com")
            .password("password")
            .role(Role.ADMIN)
            .build();
        authenticatedUser = new AuthenticatedUser(user);
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = authenticatedUser.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ADMIN", authorities.toArray()[0].toString());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", authenticatedUser.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals("test@example.com", authenticatedUser.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(authenticatedUser.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(authenticatedUser.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(authenticatedUser.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(authenticatedUser.isEnabled());
    }
}