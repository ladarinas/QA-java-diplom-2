import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditUserTests {
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @Description("Изменение пароля с авторизацией")
    public void editUserPasswordWithAuthTest() {
        User userData = User.getRandom();
        Response user = userClient.create(userData);
        userClient.login(userData.email, userData.password);
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        Response response = userClient.edit(userData.email, newPassword, userData.username, user.getBody().path("accessToken"));
        assertEquals(200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    @Description("Изменение имени с авторизацией")
    public void editUserUsernameWithAuthTest() {
        User userData = User.getRandom();
        String accessToken = userClient.create(userData).getBody().path("accessToken");
        userClient.login(userData.email, userData.password);
        String newUsername = RandomStringUtils.randomAlphabetic(10);
        Response response = userClient.edit(userData.email, userData.password, newUsername, accessToken);
        assertEquals(200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    @Description("Изменение email с авторизацией")
    public void editUserEmailWithAuthTest() {
        User userData = User.getRandom();
        String accessToken = userClient.create(userData).getBody().path("accessToken");
        userClient.login(userData.email, userData.password);
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        Response response = userClient.edit(newEmail, userData.password, userData.username, accessToken);
        assertEquals( 200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    @Description("Нельзя изменить email на уже существующий")
    public void editUserAlreadyExistsEmailWithAuthTest() {
        User userData = User.getRandom();
        String accessToken = userClient.create(userData).getBody().path("accessToken");
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        userClient.create(newEmail, userData.password, userData.username);
        userClient.login(userData.email, userData.password);
        Response response = userClient.edit(newEmail, userData.password, userData.username, accessToken);
        assertEquals(403, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("User with such email already exists", response.path("message"));
    }

    @Test
    @Description("Нельзя изменить пароль без авторизации")
    public void editUserPasswordWithoutAuthTest() {
        User userData = User.getRandom();
        userClient.create(userData);
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        Response response = userClient.edit(userData.email, newPassword, userData.username, "");
        assertEquals(401, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("You should be authorised", response.path("message"));
    }

    @Test
    @Description("Нельзя изменить имя без авторизации")
    public void editUserUsernameWithoutAuthTest() {
        User userData = User.getRandom();
        userClient.create(userData);
        String newUsername = RandomStringUtils.randomAlphabetic(10);
        Response response = userClient.edit(userData.email, userData.password, newUsername, "");
        assertEquals(401, response.statusCode());
        assertFalse( response.path("success"));
        assertEquals("You should be authorised", response.path("message"));
    }

    @Test
    @Description("Нельзя изменить email без авторизации")
    public void editUserEmailWithoutAuthTest() {
        User userData = User.getRandom();
        userClient.create(userData);
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        Response response = userClient.edit(newEmail, userData.password, userData.username, "");
        assertEquals(401, response.statusCode());
        assertFalse(response.path("success"));
        assertEquals("You should be authorised", response.path("message"));
    }
}
