import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Тесты авторизации Курьера")
public class LoginCourierTest {
    File json = new File("src/test/resources/newCourier.json");
    File jsonNoLogin = new File("src/test/resources/jsonNoLogin.json");
    File jsonWrongLogin = new File("src/test/resources/jsonWrongLogin.json");
    File jsonNoPassword = new File("src/test/resources/jsonNoPassword.json");
    File jsonWrongPassword = new File("src/test/resources/jsonWrongPassword.json");
    //        или строкой
//        String json = "{\"login\": \"eusatenko\", \"password\": \"123456\", \"firstName\": \"Evgeny\"}";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Тест авторизации Курьера")
    @Story("Логин курьера")
    @Description("курьер может авторизоваться, успешный запрос возвращает id")
    public void CheckLoginCourier() {
        Courier courier = new Courier();
        courier.createNewCourierAndGetResponse(json); // создаём и проверяем создание курьера
        courier.getResponseLoginCourier(json).then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

    @Test
    @DisplayName("Тест авторизации без Логина")
    @Story("Логин курьера")
    @Description("для авторизации нужно передать все обязательные поля, без Логина")
    public void CheckLoginCourierWithoutLogin() {
        Courier courier = new Courier();
        courier.createNewCourierAndGetResponse(json); // создаём и проверяем создание курьера
        courier.getResponseLoginCourier(jsonNoLogin).then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

    @Test
    @DisplayName("Тест авторизации без Пароля")
    @Story("Логин курьера")
    @Description("для авторизации нужно передать все обязательные поля, без Пароля")
    public void CheckLoginCourierWithoutPassword() {
        Courier courier = new Courier();
        courier.createNewCourierAndGetResponse(json); // создаём и проверяем создание курьера
        courier.getResponseLoginCourier(jsonNoPassword)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

    @Test
    @DisplayName("Тест авторизации с не правильным/не существующим логином")
    @Story("Логин курьера")
    @Description("система вернёт ошибку, если неправильно указать логин")
    public void CheckLoginCourierWrongLogin() {
        Courier courier = new Courier();
        courier.createNewCourierAndGetResponse(json); // создаём и проверяем создание курьера
        courier.getResponseLoginCourier(jsonWrongLogin)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

    @Test
    @DisplayName("Тест авторизации с неправильным паролем")
    @Story("Логин курьера")
    @Description("система вернёт ошибку, если неправильно указать пароль")
    public void CheckLoginCourierWrongPassword() {
        Courier courier = new Courier();
        courier.createNewCourierAndGetResponse(json); // создаём и проверяем создание курьера
        courier.getResponseLoginCourier(jsonWrongPassword)
                .then()
//                .assertThat().body("message", notNullValue()) // проверка, что сообщение есть
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

}
