import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {
    private static final String INGREDIENTS_PATH = "api/ingredients";

    @Step("Получить ингредиенты")
    public Response getIngredients() {
        return given()
                .spec(getBaseSpec())
                .get(INGREDIENTS_PATH);
    }
}