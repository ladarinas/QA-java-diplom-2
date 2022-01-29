import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {

    @Step
    public Response getIngredients() {
        return given()
                .spec(getBaseSpec())
                .get("api/ingredients");
    }
}