import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Тесты создания заказа с разными цветами")
@RunWith(Parameterized.class)
public class OrderTest {
    private List<String> color = null;

    @Parameterized.Parameters (name = "Цвет самоката: {0}")
    public static Object[][] getCredentials() {
        return new Object[][] {
                { List.of("Black")},
                { List.of("Gray")},
                { List.of("Black", "Gray")},
                { List.of("")},
        };
    }

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Тест создания заказа с разным цветом самоката или без указания цвета")
    @Story("Заказ")
    @Description("При создании заказа можно указать один из цветов — BLACK или GREY, " +
            "оба цвета или без указания цвета")
    public void checkCreateOrder(){
        Order order = new Order("Evgen",
                "Usatenko",
                "funny st. 15",
                5,
                "+71111111111",
                1,
                "2025-06-06",
                "как можно быстрее",
                new String[]{color.toString()});
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
        // проверяем ответ
        response.then().assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
