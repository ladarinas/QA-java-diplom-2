import org.apache.commons.lang3.RandomStringUtils;

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

    public User setEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;
        return this;
    }

    public User setEmailAndName(String email, String username) {
        this.email = email;
        this.username = username;
        return this;
    }

    public User setNameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    public static User getWithEmailAndPassword() {
        return new User().setEmailAndPassword(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru", RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithEmailAndName() {
        return new User().setEmailAndName(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru", RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithNameAndPassword() {
        return new User().setNameAndPassword(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
    }
}
