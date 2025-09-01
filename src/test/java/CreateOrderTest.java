import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.OrderSteps;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;


import static constants.Urls.URL;

public class CreateOrderTest extends TestBase {

    @ParameterizedTest
    @MethodSource("orderParam")
    @DisplayName("Создание заказа")
    @Description(value = "Изменение цветов в заказе")
    public void creatingOrderSuccess(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        OrderSteps orderSteps = new OrderSteps();
        Response createOrderResponse = orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        orderSteps.checkOrderTrackNotNullNew(createOrderResponse);
    }

    static Stream<Arguments> orderParam() {
        return Stream.of(
                Arguments.of("Алексей", "Петров", "Москва, ул. Тверская, д. 15", "5", "+7 900 123 45 67", 3, "2024-07-15", "Позвонить за час", Arrays.asList("BLACK")),
                Arguments.of("Мария", "Иванова", "Санкт-Петербург, Невский пр., д. 28", "12", "+7 911 222 33 44", 2, "2024-07-16", "Оставить у консьержа", Arrays.asList("GREY")),
                Arguments.of("Дмитрий", "Сидоров", "Екатеринбург, ул. Ленина, д. 42", "8", "+7 912 345 67 89", 5, "2024-07-17", "Код домофона 123", Arrays.asList("BLACK", "GREY")),
                Arguments.of("Ольга", "Кузнецова", "Новосибирск, пр. Карла Маркса, д. 7", "3", "+7 913 456 78 90", 1, "2024-07-18", "Без комментариев", Arrays.asList())
        );
    }
}