package pl.tasklist.tasklistbackend.login;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.dto.UserRegisterDTO;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class LoginIT {

    private static final String USERNAME = "test";
    private static final String PASSWORD = "aA1!";

    @BeforeClass
    public static void before(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername(USERNAME);
        userRegisterDTO.setPassword(PASSWORD);
        given()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("/api/register");
    }

    @Test
    public void when_user_have_correct_credentials_then_OK(){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(USERNAME);
        userLoginDTO.setPassword(PASSWORD);
        given()
            .when()
                .body(userLoginDTO)
                .contentType(ContentType.JSON)
                .post("/api/login")
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void when_wrong_password_then_UNAUTHORIZED(){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(USERNAME);
        userLoginDTO.setPassword(PASSWORD+"wrong");
        given()
                .when()
                .body(userLoginDTO)
                .contentType(ContentType.JSON)
                .post("/api/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
