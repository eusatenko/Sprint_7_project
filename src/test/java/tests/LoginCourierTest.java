package tests;

import metods.Courier;
import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

@DisplayName("Тесты авторизации Курьера")
public class LoginCourierTest extends BaseTest {
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
    public void setDown(){
        System.out.println("ID удалённого курьера " + idCourier);
        courier.deleteCourier(idCourier);
    }
    @Test
    @DisplayName("Тест авторизации Курьера")
    @Story("Логин курьера")
    @Description("курьер может авторизоваться, успешный запрос возвращает id")
    public void checkLoginCourier() {
        courier.createNewCourierAndGetResponse(courierData); // создаём и проверяем создание курьера
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData); // авторизуемся курьером
        responseLoginCourier
                .then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);

        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString(); // вытаскиваем id Курьера через авторизацию
    }

    @Test
    @DisplayName("Тест авторизации без Логина")
    @Story("Логин курьера")
    @Description("для авторизации нужно передать все обязательные поля, без Логина")
    public void checkLoginCourierWithoutLogin() {
        courier.createNewCourierAndGetResponse(courierData); // создаём курьера
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setLogin("");
        courier.getResponseLoginCourier(courierData)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString(); // вытаскиваем id Курьера через авторизацию
    }

    @Test
    @DisplayName("Тест авторизации без Пароля")
    @Story("Логин курьера")
    @Description("для авторизации нужно передать все обязательные поля, без Пароля")
    public void checkLoginCourierWithoutPassword() {
        courier.createNewCourierAndGetResponse(courierData); // создаём и проверяем создание курьера
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setPassword("");
        courier.getResponseLoginCourier(courierData)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString(); // вытаскиваем id Курьера через авторизацию
    }

    @Test
    @DisplayName("Тест авторизации с не правильным/не существующим логином")
    @Story("Логин курьера")
    @Description("система вернёт ошибку, если неправильно указать логин")
    public void checkLoginCourierWrongLogin() {
        courier.createNewCourierAndGetResponse(courierData); // создаём и проверяем создание курьера
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setLogin("UsaTest");
        courier.getResponseLoginCourier(courierData)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString(); // вытаскиваем id Курьера через авторизацию
    }

    @Test
    @DisplayName("Тест авторизации с неправильным паролем")
    @Story("Логин курьера")
    @Description("система вернёт ошибку, если неправильно указать пароль")
    public void checkLoginCourierWrongPassword() {
        courier.createNewCourierAndGetResponse(courierData); // создаём и проверяем создание курьера
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setPassword("Qwe");
        courier.getResponseLoginCourier(courierData)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString(); // вытаскиваем id Курьера через авторизацию
    }
}
