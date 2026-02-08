package com.codfish.bikeSalesAndService.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodfishBikeUserDetailsServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private CodfishBikeUserDetailsService userDetailsService;

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        // given
        String username = "nonexistentUser";
        when(userJpaRepository.findByUserName(username)).thenReturn(null);

        // when & then
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });
    }
}
