import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class EditUserTests {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User();
    }

    @Test
    public void editUserPasswordWithAuth() {
        Map<String, String> userData = user.createRandomUser();
        userClient.login(userData.get("email"), userData.get("password"));
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        Response response = userClient.edit(userData.get("email"), newPassword, userData.get("username"), userData.get("accessToken") );
        assertEquals(200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    public void editUserUsernameWithAuth() {
        Map<String, String> userData = user.createRandomUser();
        userClient.login(userData.get("email"), userData.get("password"));
        String newUsername = RandomStringUtils.randomAlphabetic(10);
        Response response = userClient.edit(userData.get("email"), userData.get("password"), newUsername, userData.get("accessToken"));
        assertEquals(200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    public void editUserEmailWithAuth() {
        Map<String, String> userData = user.createRandomUser();
        userClient.login(userData.get("email"), userData.get("password"));
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        Response response = userClient.edit(newEmail, userData.get("password"), userData.get("name"), userData.get("accessToken"));
        assertEquals( 200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    public void editUserAlreadyExistsEmailWithAuth() {
        Map<String, String> userData = user.createRandomUser();
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        userClient.create(newEmail, userData.get("password"), userData.get("name"));
        userClient.login(userData.get("email"), userData.get("password"));
        Response response = userClient.edit(newEmail, userData.get("password"), userData.get("name"), userData.get("accessToken"));
        assertEquals(403, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("User with such email already exists", response.path("message"));
    }

    @Test
    public void editUserPasswordWithoutAuth() {
        Map<String, String> userData = user.createRandomUser();
        Response response = userClient.edit(userData.get("email"), userData.get("password"), userData.get("name"), "");
        assertEquals(401, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("You should be authorised", response.path("message"));
    }

    @Test
    public void editUserUsernameWithoutAuth() {
        Map<String, String> userData = user.createRandomUser();
        Response response = userClient.edit(userData.get("email"), userData.get("password"), userData.get("name"), "");
        assertEquals(401, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("You should be authorised", response.path("message"));
    }

    @Test
    public void editUserEmailWithoutAuth() {
        Map<String, String> userData = user.createRandomUser();
        Response response = userClient.edit(userData.get("email"), userData.get("password"), userData.get("name"), "");
        assertEquals(401, response.statusCode());
        assertFalse(response.path("success"));
        assertEquals("You should be authorised", response.path("message"));
    }
}
