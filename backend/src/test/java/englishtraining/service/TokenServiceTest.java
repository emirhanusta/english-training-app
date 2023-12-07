package englishtraining.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    private TokenService tokenService;

    private Authentication authentication;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        authentication = mock(Authentication.class);
        userDetails = mock(UserDetails.class);
        ReflectionTestUtils.setField(tokenService, "KEY", "englishtraining");
    }
    @Test
    void shouldGenerateValidToken() {
        // given
        String username = "testUser";
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        // when
        String token = tokenService.generateToken(authentication);

        // then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void shouldVerifyValidToken() {
        // given
        String username = "testUser";
        String token = generateValidToken(username);

        // when
        DecodedJWT decodedJWT = tokenService.verifyJWT(token);

        // then
        assertNotNull(decodedJWT);
        assertEquals(username, decodedJWT.getSubject());
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        // given
        String invalidToken = "invalidToken";

        // then
        assertThrows(RuntimeException.class, () -> tokenService.verifyJWT(invalidToken));
    }

    private String generateValidToken(String username) {
        return tokenService.generateToken(createMockAuthentication(username));
    }

    private Authentication createMockAuthentication(String username) {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        return authentication;
    }
}
