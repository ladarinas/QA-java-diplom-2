import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CreateUserTests {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User();
    }

    @Test
    public void createUser() {
        Map<String,String> userData = user.getRandom();
        Response response = userClient.create(userData.get("email"), userData.get("password"), userData.get("username"));
        assertEquals(200, response.statusCode());
    }
    @Test
    public void createExistsUser() {
        Map<String,String> userData = user.getRandom();
        userClient.create(userData.get("email"), userData.get("password"), userData.get("username"));
        Response response = userClient.create(userData.get("email"), userData.get("password"), userData.get("username"));
        assertEquals(403, response.statusCode());
        assertEquals("User already exists", response.path("message"));
    }
    
    @Test
    public void createUserWithoutEmail() {
        Map<String,String> userData = user.getRandom();
        Response response = userClient.create(null, userData.get("password"), userData.get("username"));
        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.path("message"));
    }

    @Test
    public void createUserWithoutPassword() {
        Map<String,String> userData = user.getRandom();
        Response response = userClient.create(userData.get("email"), null, userData.get("username"));
        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.path("message"));
    }

    @Test
    public void createUserWithoutUsername() {
        Map<String,String> userData = user.getRandom();
        Response response = userClient.create(userData.get("email"),  userData.get("password"), null);
        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.path("message"));
    }
}