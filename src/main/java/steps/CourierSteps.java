package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.CourierCreate;
import serialization.CourierLogin;

import static constants.Urls.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.*;
public class CourierSteps {
    @Step("Запрос создание курьера")
    public Response createCourier(String login, String pass, String name) {
        CourierCreate courier = new CourierCreate(login, pass, name);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATE);
    }
    @Step("Запрос Логин курьера в системе")
    public Response loginCourier(String login, String pass) {
        CourierLogin loginCourier = new CourierLogin(login, pass);
        return given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .when()
                .post(COURIER_LOGIN);
    }
    @Step("Найти id курьера")
    public Integer getCourierId(String login, String pass) {
        return loginCourier(login, pass)
                .body()
                .as(CourierCreate.class)
                .getId();
    }
    @Step("Удалить курьера")
    public Response deleteCourier(String login, String pass) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(courierDeletePreparingToString(getCourierId(login, pass)));
    }
    @Step("Подготовка запроса на удаление курьера")
    public String courierDeletePreparingToString(Integer courierID) {
        return COURIER_DEL + courierID;
    }
    @Step("Ответ Создание курьера")
    public void checkAnswerValidRegistration(Response response) {
        response
                .then()
                .statusCode(201)
                .and()
                .assertThat()
                .body("ok", equalTo(true));
    }
    @Step("Ответ Удаление курьера")
    public void checkAnswerThenValidDeleting(Response response) {
        response
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("ok", equalTo(true));
    }
    @Step("Ответ Создание курьера. Этот логин уже используется. 409")
    public void checkAnswerReuseRegistrationData(Response response) {
        response.then()
                .statusCode(409)
                .and()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Step("Ответ Создание курьера. Недостаточно данных для создания учетной записи. 400")
    public void checkAnswerWithNotEnoughRegData(Response response) {
        response.then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Step("Ответ  Логин курьера в системе. id не пуст")
    public void checkAnswerAndPresenceId(Response response) {
        response.then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("id", notNullValue());
    }
    @Step("Ответ  Логин курьера в системе. Учетная запись не найдена. 404")
    public void checkAnswerWithWrongData(Response response) {
        response.then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Step("Ответ  Логин курьера в системе. Недостаточно данных для входа. 400")
    public void checkAnswerWithoutData(Response response) {
        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}