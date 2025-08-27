import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.OrderSteps;

import static constants.Urls.URL;


public class GetOrderListTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Список всех заказов системы как файл json")
    public void getOrderListNotNull() {
        OrderSteps orderStep = new OrderSteps();
        Response response = orderStep.getOrdersList();
        orderStep.checkOrderListNotNullNew(response);
    }
}