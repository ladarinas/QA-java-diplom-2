import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String email;
    public String password;
    public String username;

    public User() {
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static User getRandom(){
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String username = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, username);
    }


    public Map<String, String> createRandomUser(){
        User userData = User.getRandom();
        UserClient userClient = new UserClient();
        String accessToken = userClient.create(userData.email, userData.password, userData.username).path("accessToken");
        Map<String, String> inputDataMap = new HashMap<>();
        inputDataMap.put("email", email);
        inputDataMap.put("password", password);
        inputDataMap.put("name", username);
        inputDataMap.put("accessToken", accessToken);
        return inputDataMap;
    }
}
