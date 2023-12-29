package dev.cleysonph.smartgym.core.services.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.cleysonph.smartgym.core.models.User;
import dev.cleysonph.smartgym.core.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void whenLoadUserByUsername_thenReturnUserDetails() {
        String username = "test@example.com";
        User user = new User();
        user.setEmail(username);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        var userDetails = authenticationService.loadUserByUsername(username);

        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void whenLoadUserByUsername_thenThrowUsernameNotFoundException() {
        String username = "test@example.com";

        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername(username);
        });
    }
}