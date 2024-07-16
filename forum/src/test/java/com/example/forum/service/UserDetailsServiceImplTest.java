package com.example.forum.service;

import com.example.forum.model.Usuario;
import com.example.forum.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        // Configurar comportamiento de usuarioRepository
        Usuario usuario = new Usuario("Test User", "testuser@example.com", "password");
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());
    }

    @Test
    public void loadUserByUsername_existingUser_returnUserDetails() {
        String email = "testuser@example.com";

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
    }

    @Test
    public void loadUserByUsername_unknownUser_throwUsernameNotFoundException() {
        String unknownEmail = "unknown@example.com";

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(unknownEmail);
        });
    }
}
