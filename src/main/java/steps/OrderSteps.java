package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.OrderCreate;
import java.util.List;

import static constants.Urls.ORDERS_LIST;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderSteps {
    @Step("Запрос создания нового заказа")
    public Response createOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        OrderCreate order = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS_LIST);
    }
    @Step("Ответ создания нового заказа не пуст")
    public void checkOrderTrackNotNullNew(Response response) {
        response
                .then()
                .statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }
    @Step("Запрос Получение списка заказов")
    public Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS_LIST);
    }
    @Step("Ответ получения списка заказов не пуст")
    public void checkOrderListNotNullNew(Response response) {
        response.then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("orders", notNullValue());
    }
}