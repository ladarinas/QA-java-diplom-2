import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "api/orders";

    @Step
    public Response createOrder(List<String> ingredients, String token) {
        JSONObject requestBodyJson = new JSONObject();
        String requestBody = requestBodyJson
                .put("ingredients", ingredients)
                .toString();
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .body(requestBody)
                .when()
                .post(ORDER_PATH);
    }

    @Step
    public Response getOrders(String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .get(ORDER_PATH);
    }
}
