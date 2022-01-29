import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CreateOrderTests {
    OrderClient orderClient;
    UserClient userClient;
    IngredientsClient ingredientsClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        userClient = new UserClient();
        ingredientsClient = new IngredientsClient();
    }

    @Test
    public void createOrderWithAuth() {
        User userData = User.getRandom();
        String accessToken = userClient.create(userData).getBody().path("accessToken");
        List<String> ingredients  = ingredientsClient.getIngredients().path("data._id");
        Response response = orderClient.createOrder(ingredients, accessToken);
        assertEquals(200, response.statusCode());
        assertTrue(response.path("success"));
    }

    @Test
    public void createOrderWithoutAuth() {
        List<String> ingredients  = ingredientsClient.getIngredients().path("data._id");
        Response response = orderClient.createOrder(ingredients, "");
        assertEquals(200, response.statusCode());
        assertTrue(response.path("success"));
    }
    @Test
    public void createOrderWithoutIngredients() {
        User userData = User.getRandom();
        String accessToken = userClient.create(userData).getBody().path("accessToken");
        Response response = orderClient.createOrder(null, accessToken);
        assertEquals(400, response.statusCode());
        assertFalse(response.path("success"));
        assertEquals("Ingredient ids must be provided", response.path("message"));
    }

    @Test
    public void createOrderWithIncorrectIngredient() {
        User userData = User.getRandom();
        String accessToken = userClient.create(userData).getBody().path("accessToken");
        Response response = orderClient.createOrder(Collections.singletonList("ingredient"), accessToken);
        assertEquals(500, response.statusCode());
    }
}
