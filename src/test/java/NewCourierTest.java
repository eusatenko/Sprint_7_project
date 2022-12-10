import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Тесты создания нового Курьера")
public class NewCourierTest {

    File json = new File("src/test/resources/newCourier.json");
    File jsonNoLogin = new File("src/test/resources/jsonNoLogin.json");
    File jsonNoPassword = new File("src/test/resources/jsonNoPassword.json");
    //        или строкой
//        String json = "{\"login\": \"eusatenko\", \"password\": \"123456\", \"firstName\": \"Evgeny\"}";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Тест создания нового курьера")
    @Story ("Создание курьера")
    @Description ("курьера можно создать")
    public void CheckCreateNewCourier() {
        Courier courier = new Courier();
        courier.CheckCreateNewCourier(json); // создаём и проверяем создание курьера
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

    @Test
    @DisplayName("Тест, что нельзя создать 2-х одинаковых курьеров")
    @Story ("Создание курьера")
    @Description ("нельзя создать двух одинаковых курьеров")
    public void CheckDoubleCreateNewCourier() {
        Courier courier = new Courier();
        courier.createNewCourierAndGetResponse(json); //создали первого курьера
        courier.createNewCourierAndGetResponse(json) // создали второго курьера и проверили ответ
                .then()
                .assertThat().body("message", notNullValue()) //сообщение из документации
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Тест создания курьера без логина")
    @Story ("Создание курьера")
    @Description ("чтобы создать курьера, нужно передать в ручку все обязательные поля. Без Логина")
    public void CheckRequiredFieldsCreateNewCourierWithoutLogin() {
    Courier courier = new Courier();
    courier.createNewCourierAndGetResponse(jsonNoLogin)
            .then().body("message", notNullValue())
            .and()
            .statusCode(400);
        courier.deleteCourier(courier.getIdCourier(json)); // удаляем курьера
    }

    @Test
    @DisplayName("Тест создания курьера без Пароля")
    @Story ("Создание курьера")
    @Description ("чтобы создать курьера, нужно передать в ручку все обязательные поля. Без Пароля")
    public void CheckRequiredFieldsCreateNewCourierWithoutPassword() {
    Courier courier = new Courier();
    courier.createNewCourierAndGetResponse(jsonNoPassword)
            .then().body("message", notNullValue())
            .and()
            .statusCode(400);
    }
}
