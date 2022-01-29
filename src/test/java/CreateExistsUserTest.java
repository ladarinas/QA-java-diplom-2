import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateExistsUserTest {
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    public void createExistsUser() {
        User userData = User.getRandom();
        userClient.create(userData.email, userData.password, userData.username);
        Response response = userClient.create(userData.email, userData.password, userData.username);
        assertEquals(403, response.statusCode());
        assertEquals("User already exists", response.path("message"));
    }
}
