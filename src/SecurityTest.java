import static org.junit.Assert.*;
import org.junit.Test;



public class SecurityTest {
    SecurityValidation securityValidator = new SecurityValidation();

    @Test
    public void verifyUsernameSpecialCharacters() {
        int result = securityValidator.comparer("tathagat panwar2", "tathagatpanwar2");
        assertEquals(0, result);
    }

    @Test
    public void verifyUsernameGranted() {
        int result = securityValidator.comparer("tathagatpanwar2", "tathagatpanwar2");
        assertEquals(1, result);
    }
}
