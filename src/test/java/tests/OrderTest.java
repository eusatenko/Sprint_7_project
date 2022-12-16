package tests;

import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import metods.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Тесты создания заказа с разными цветами")
@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
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

    @Test
    @DisplayName("Тест создания заказа с разным цветом самоката или без указания цвета")
    @Story("Заказ")
    @Description("При создании заказа можно указать один из цветов — BLACK или GREY, " +
            "оба цвета или без указания цвета")
    public void checkCreateOrder(){
        Order order = new Order();
        OrderData orderData = new OrderData("Evgen",
                "Usatenko",
                "funny st. 15",
                5,
                "+71111111111",
                1,
                "2025-06-06",
                "как можно быстрее",
                new String[]{color.toString()});
        // проверяем ответ
        order.createOrderGetResponse(orderData).then().assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }
}
