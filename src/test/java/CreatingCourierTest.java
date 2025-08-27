import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.CourierSteps;

import static constants.RandomData.*;
import static constants.Urls.URL;

public class CreatingCourierTest {

    CourierSteps courierSteps;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = URL;
        courierSteps = new CourierSteps();
    }


    @Test
    @DisplayName("Создание нового курьера")
    @Description("Создайте нового курьера с правильными учетными данными и отметьте положительный результат создания курьера")
    public void creatingCourierPositive() {
        Response responseCreate = courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, RANDOM_NAME);
        courierSteps.checkAnswerValidRegistration(responseCreate);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Создание идентичных курьеров")
    @Description("Проверка ответа (кода состояния и текста сообщения) при попытке создать идентичных курьеров")
    public void creatingIdenticalCouriersConflict() {
        courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, RANDOM_NAME);
        Response responseIdentical = courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, RANDOM_NAME);
        courierSteps.checkAnswerReuseRegistrationData(responseIdentical);
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Создание курьера с существующим логином и паролем, проверка ответа")
    public void creatingCourierWithExistingLoginConflict() {
        courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, RANDOM_NAME);
        Response responseExisting = courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, RANDOM_NAME);
        courierSteps.checkAnswerReuseRegistrationData(responseExisting);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера без логина и проверки ответа")
    public void creatingCourierWithoutLoginBadRequest() {
        Response responseWithoutLogin = courierSteps.createCourier("", RANDOM_PASSWORD, RANDOM_NAME);
        courierSteps.checkAnswerWithNotEnoughRegData(responseWithoutLogin);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера без пароля и проверки ответа")
    public void creatingCourierWithoutPasswordBadRequest() {
        Response responseWithoutPass = courierSteps.createCourier(RANDOM_LOGIN, "", RANDOM_NAME);
        courierSteps.checkAnswerWithNotEnoughRegData(responseWithoutPass);
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Создание курьера без имени и проверки ответа")
    public void creatingCourierWithoutNamePositive() {
        Response responseWithoutName = courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, "");
        courierSteps.checkAnswerValidRegistration(responseWithoutName);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

}