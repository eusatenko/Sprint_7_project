package metods;

import data.ApiUrl;
import data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order {

    @Step("Создание заказа и получение ответа")
    public Response createOrderGetResponse(OrderData orderData){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(orderData)
                        .when()
                        .post(ApiUrl.ORDERS);
        return response;
    }

    @Step("Получение списка заказов")
    public Response getListOfOrders(){
        Response response = given()
                .get(ApiUrl.ORDERS);
        return response;
    }
}
