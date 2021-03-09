package pl.tasklist.tasklistbackend.login;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.payload.LoginRequest;
import pl.tasklist.tasklistbackend.payload.RegisterRequest;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class LoginIT {

    private static final String USERNAME = "test";
    private static final String PASSWORD = "aA1!";

    @BeforeClass
    public static void before(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(USERNAME);
        registerRequest.setPassword(PASSWORD);
        given()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("/api/register");
    }

    @Test
    public void when_user_have_correct_credentials_then_OK(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(USERNAME);
        loginRequest.setPassword(PASSWORD);
        given()
            .when()
                .body(loginRequest)
                .contentType(ContentType.JSON)
                .post("/api/login")
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void when_wrong_password_then_UNAUTHORIZED(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(USERNAME);
        loginRequest.setPassword(PASSWORD+"wrong");
        given()
                .when()
                .body(loginRequest)
                .contentType(ContentType.JSON)
                .post("/api/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void when_user_does_not_exist_then_UNAUTHORIZED(){
        LoginRequest loginRequest = new LoginRequest();
        given()
                .when()
                .body(loginRequest)
                .contentType(ContentType.JSON)
                .post("/api/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
