import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Получение списка заказов")
public class ListOfOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Тест получения списка заказов")
    @Description("Получаем список заказов")
    public void checkListOfOrderNotNull() {
        given()
                .get("/api/v1/orders")
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
