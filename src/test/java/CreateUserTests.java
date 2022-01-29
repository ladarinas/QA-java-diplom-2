import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateUserTests {
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    public void createUser() {
        User userData = User.getRandom();
        Response response = userClient.create(userData.email, userData.password, userData.username);
        assertEquals(200, response.statusCode());
    }
    @Test
    public void createExistsUser() {
        User userData = User.getRandom();
        userClient.create(userData.email, userData.password, userData.username);
        Response response = userClient.create(userData.email, userData.password, userData.username);
        assertEquals(403, response.statusCode());
        assertEquals("User already exists", response.path("message"));
    }
    
    @Test
    public void createUserWithoutEmail() {
        User userData = User.getRandom();
        Response response = userClient.create(null, userData.password, userData.username);
        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.path("message"));
    }

    @Test
    public void createUserWithoutPassword() {
        User userData = User.getRandom();
        Response response = userClient.create(userData.email, null, userData.username);
        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.path("message"));
    }

    @Test
    public void createUserWithoutUsername() {
        User userData = User.getRandom();
        Response response = userClient.create(userData.email,  userData.password, null);
        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.path("message"));
    }
}