package tests;

import metods.Courier;
import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;


@DisplayName("Тесты создания нового Курьера")
public class NewCourierTest extends BaseTest {
    private Courier courier;
    private CourierData courierData;
    private String idCourier = "0";

    @Before
    @Override
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = new Courier();
        courierData = new CourierData();
    }
    @After
    public void deleteCourier(){
        System.out.println("ID удалённого курьера " + idCourier);
        courier.deleteCourier(idCourier);
    }


    @Test
    @DisplayName("Тест создания нового курьера")
    @Story ("Создание курьера")
    @Description ("курьера можно создать")
    public void checkCreateNewCourier() {
        courier.createNewCourierAndGetResponse(courierData)
                .then()
                .assertThat()
                .body("ok",equalTo(true))
                .and()
                .statusCode(SC_CREATED); // создаём и проверяем создание курьера

        idCourier = courier.getResponseLoginCourier(courierData)
                .then()
                .extract()
                .path("id").toString(); // вытаскиваем id Курьера через авторизацию
    }

    @Test
    @DisplayName("Тест, что нельзя создать 2-х одинаковых курьеров")
    @Story ("Создание курьера")
    @Description ("нельзя создать двух одинаковых курьеров")
    public void checkDoubleCreateNewCourier() {
        courier.createNewCourierAndGetResponse(courierData); //создали первого курьера
        courier.createNewCourierAndGetResponse(courierData) // создали второго курьера и проверили ответ
                .then()
                .assertThat()
                .body("message", notNullValue()) //сообщение из документации
                .and()
                .statusCode(SC_CONFLICT);
        idCourier = courier.getResponseLoginCourier(courierData)
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Тест создания курьера без логина")
    @Story ("Создание курьера")
    @Description ("чтобы создать курьера, нужно передать в ручку все обязательные поля. Без Логина")
    public void checkRequiredFieldsCreateNewCourierWithoutLogin() {
    courierData.setLogin("");
    courier.createNewCourierAndGetResponse(courierData)
            .then().body("message", notNullValue())
            .and()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Тест создания курьера без Пароля")
    @Story ("Создание курьера")
    @Description ("чтобы создать курьера, нужно передать в ручку все обязательные поля. Без Пароля")
    public void checkRequiredFieldsCreateNewCourierWithoutPassword() {
    courierData.setPassword("");
    courier.createNewCourierAndGetResponse(courierData)
            .then().body("message", notNullValue())
            .and()
            .statusCode(SC_BAD_REQUEST);
    }
}
