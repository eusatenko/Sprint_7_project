import io.restassured.response.Response;
import org.junit.After;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Courier {
//    private File json;

//    метод создания курьера и получения тела ответа
    public Response createNewCourierAndGetResponse(File json) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/");
        return response;
    }

//    проверка создания курьера
    public void CheckCreateNewCourier(File json) {
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

    public int getIdCourier(File json) {
        int idCourier = given().
                contentType("application/json").
                body(json).
                when().
                post("/api/v1/courier/login").
                then().
                statusCode(200).
                extract().
                path("id");
        return idCourier;
    }

    public Response getResponseLoginCourier(File json) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

//    удаляем курьера
    public void deleteCourier(int idCourier) {
        // вытаскиваем ID курьера

        // удаляем курьера
        given()
                .auth().oauth2("")
                .delete("/api/v1/courier/" + idCourier)
                .then().statusCode(200);
    }
}
