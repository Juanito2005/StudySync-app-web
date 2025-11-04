package dev.juanito.studysync.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import dev.juanito.studysync.model.User;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;
    private User testUser;

    // Fake scennary that is going to be used in each test
    @BeforeEach
    void setUp() {

        // Create a fake JwtService adding propperties like if we were Spring
        jwtService = new JwtService();
        /*ReflectionTestUtils.setField() recibe como parametros: la clase, el atributo a modificar
        y el valor con el que quiere ser modificado ese atributo*/
        ReflectionTestUtils.setField(jwtService, "secretKey", "IXX+Kig+UFDg9xpiEauNW0aqzfHjOrrREoeQjmEDLkI=");
        ReflectionTestUtils.setField(jwtService, "expirationMillis", 86400000L);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");
    }

    @Test
    void validateToken_WithValidSecret_ShouldReturnTrue() {

        //Given a good token with the correct secret
        String validToken = jwtService.createToken(testUser);

        //When we validate the token
        boolean result = jwtService.validateToken(validToken);

        //Then: this shit must return a true
        assertTrue(result, "Token created with correc secret should be valid");
    }

    @Test
    void validateToken_WithInvalidSecret_ShouldReturnFalse() {
        String validToken = jwtService.createToken(testUser);
        ReflectionTestUtils.setField(jwtService, "secretKey", "DIFFERENT_SECRET_KEYT_THAT_IS_WRONG123=");
        boolean result = jwtService.validateToken(validToken);
        assertFalse(result, "Token should be invalid when validated with different secret");
    }

    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() {
        // A few milis has been rested from the expiration attribute so the token will be always expired
        ReflectionTestUtils.setField(jwtService, "expirationMillis", -10L);
        String validToken = jwtService.createToken(testUser);
        boolean result = jwtService.validateToken(validToken);
        assertFalse(result, "Token should be invalid because it's time has expired");
    }

    @Test
    void validateToken_WithTamperedToken_ShouldReturnFalse() {
        String validToken = jwtService.createToken(testUser);
        String tamperedToken = validToken.replace(validToken.substring(1), "Q");
        boolean result = jwtService.validateToken(tamperedToken);
        assertFalse(result, "Token should be invalid because has been modified is false");
    }

}

// @Test
// void testCreateToken() {

// }

// @Test
// void testGetExpirationMillis() {

// }

// @Test
// void testGetSecretKey() {

// }

// @Test
// void testGetSubject() {

// }

// @Test
// void testGetUserIdFromToken() {

// }
