package tests;

import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import metods.Order;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;


@DisplayName("Получение списка заказов")
public class ListOfOrdersTest extends BaseTest {

    @Test
    @DisplayName("Тест получения списка заказов")
    @Description("Получаем список заказов")
    public void checkListOfOrderNotNull() {
        Order order = new Order();
        OrderData orderData = new OrderData();
        order.getListOfOrders()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
