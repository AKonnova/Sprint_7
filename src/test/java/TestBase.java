import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static constants.Urls.URL;

public class TestBase {

    @BeforeAll
    public static void globalSetUp() {
        RestAssured.baseURI = URL;
    }
}