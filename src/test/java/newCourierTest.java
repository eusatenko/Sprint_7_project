import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class newCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @Description ("курьера можно создать")
    public void CheckCreateNewCourier() {
//        File json = new File("src/test/resources/newCourier.json");
//        или строкой
        String json = "{\"login\": \"eusatenko\", \"password\": \"123456\", \"firstName\": \"Evgeny\"}";;

        // создаём курьера и записываем ответ
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/");
        // проверяем ответ
        response.then().assertThat().body("ok",equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @Description ("нельзя создать двух одинаковых курьеров")
    public void CheckDoubleCreateNewCourier() {
        File json = new File("src/test/resources/newCourier.json");
//        или строкой
//        String json = "{\"login\": \"eusatenko\", \"password\": \"123456\", \"firstName\": \"Evgeny\"}";;

        // создаём первого курьера
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/")
                .then().assertThat().body("ok",equalTo(true))
                .and()
                .statusCode(201);
        // создаём второго курьера с теми же данными
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/");

        response.then().assertThat().body("message",equalTo("Этот логин уже используется")) //сообщение из документации
                .and()
                .statusCode(409);
    }

    @Test
    @Description ("чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void CheckRequiredFieldsCreateNewCourier() {

    }

    @After
    public void deleteCourier() {

        String json = "{\"login\": \"eusatenko\", \"password\": \"123456\"}";
        // вытаскиваем ID курьера
        int courierId =
                given().
                        contentType("application/json").
                        body(json).
                        when().
                        post("/api/v1/courier/login").
                        then().
                        statusCode(200).
                        extract().
                        path("id");

        // удаляем курьера
        given()
                .auth().oauth2("")
                .delete("/api/v1/courier/" + courierId)
                .then().statusCode(200);
    }

}
