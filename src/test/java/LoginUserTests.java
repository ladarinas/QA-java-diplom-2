
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LoginUserTests {

    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User();
    }

    @Test
    public void loginUser() {
        Map<String, String> userData = user.createRandomUser();
        Response response = userClient.login(userData.get("email"), userData.get("password"));
        assertEquals(200, response.statusCode());
        assertTrue( response.path("success"));
    }

    @Test
    public void loginUserWithIncorrectPassword() {
        Map<String, String> userData = user.createRandomUser();
        Response response = userClient.login(userData.get("email"), "test");
        assertEquals(401, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("email or password are incorrect", response.path("message"));
    }
}
