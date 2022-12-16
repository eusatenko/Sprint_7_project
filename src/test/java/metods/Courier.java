package metods;

import data.ApiUrl;
import data.CourierData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {

    //    метод создания курьера и получения тела ответа
    @Step("Создание курьера и получение ответа")
    public Response createNewCourierAndGetResponse(CourierData courierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("")
                .and()
                .body(courierData)
                .when()
                .post(ApiUrl.COURIER);
        return response;
    }

    @Step("Авторизация/Логин Курьером и получение ответа")
    public Response getResponseLoginCourier(CourierData courierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierData)
                .when()
                .post(ApiUrl.LOGIN);
        return response;
    }

//    удаляем курьера
    @Step("Удаление Курьера")
    public void deleteCourier(String idCourier) {
        given()
                .auth().oauth2("")
                .delete(ApiUrl.COURIER + idCourier);
    }
}
