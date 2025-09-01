import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.CourierSteps;

import static constants.RandomData.*;

public class LoginCourierTest extends TestBase {

    private CourierSteps courierSteps = new CourierSteps();

    @BeforeEach
    public void setUp() {
        // Создаем курьера перед каждым тестом
        courierSteps.createCourier(RANDOM_LOGIN, RANDOM_PASSWORD, RANDOM_NAME);
    }

    @Test
    @DisplayName("Успешный вход курьера в систему")
    @Description("При вводе действительного пароля и логина при успешном выполнении запроса возвращается id курьера")
    public void loginCourierSuccess() {
        Response loginResponse = courierSteps.loginCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerAndPresenceId(loginResponse);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход курьера в систему с неправильным логином курьера")
    @Description("Создаем нового курьера, входим в систему с неверным логином курьера и проверяем, что курьер не смог войти в систему, код состояния=404")
    public void loginCourierWithIncorrectLoginFailed() {
        Response wrongLoginResponse = courierSteps.loginCourier("wrongLogin", RANDOM_PASSWORD);
        courierSteps.checkAnswerWithWrongData(wrongLoginResponse);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход курьера в систему с неверным паролем курьера")
    @Description("Создаем нового курьера, входим в систему с неверным паролем курьера и проверяем, что курьер не смог войти в систему, код состояния=404")
    public void loginCourierWithIncorrectPassFailed() {
        Response wrongPassResponse = courierSteps.loginCourier(RANDOM_LOGIN, "987");
        courierSteps.checkAnswerWithWrongData(wrongPassResponse);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход курьера в систему без логина")
    @Description("Создаем нового курьера, входим в систему без логина и проверяем неудачный вход курьера, код состояния=400")
    public void loginCourierWithoutLoginFailed() {
        Response withoutLoginResponse = courierSteps.loginCourier("", RANDOM_PASSWORD);
        courierSteps.checkAnswerWithoutData(withoutLoginResponse);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход курьера в систему без пароля")
    @Description("Создаем нового курьера, входим в систему без пароля и проверяем неудачный вход курьера, код состояния=400")
    public void loginCourierWithoutPassFailed() {
        Response withoutPassResponse = courierSteps.loginCourier(RANDOM_LOGIN, "");
        courierSteps.checkAnswerWithoutData(withoutPassResponse);
        Response responseDelete = courierSteps.deleteCourier(RANDOM_LOGIN, RANDOM_PASSWORD);
        courierSteps.checkAnswerThenValidDeleting(responseDelete);
    }
}