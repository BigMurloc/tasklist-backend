package pl.tasklist.tasklistbackend.user;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.payload.RegisterRequest;
import pl.tasklist.tasklistbackend.entity.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class RegisterIT {

    private static final String CORRECT_PASSWORD_EXAMPLE = "aA1!";

    @BeforeClass
    public static void before(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("alreadyExists");
        registerRequest.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .body(registerRequest)
            .contentType(ContentType.JSON)
            .post("api/register");
    }

    @Test
    public void when_new_user_then_CREATED(){
        User userDTO = new User();
        userDTO.setUsername("test");
        userDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void when_user_already_exists_then_CONFLICT(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("alreadyExists");
        registerRequest.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.CONFLICT.value())
            .and()
                .content(equalTo("User already exists!"));
    }

    @Test
    public void when_username_is_blank_then_BAD_REQUEST(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("");
        registerRequest.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void when_username_is_less_than_4_characters_long_then_BAD_REQUEST(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("tes");
        registerRequest.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_username_is_longer_than_16_characters_then_BAD_REQUEST(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Longerthan16charactersforsure");
        registerRequest.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_username_does_not_match_regex_then_BAD_REQUEST(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("tester!");
        registerRequest.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
                .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_password_is_blank_then_BAD_REQUEST(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test");
        registerRequest.setPassword("");
        given()
                .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("api/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_password_does_not_match_regex_then_BAD_REQUEST(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test");
        registerRequest.setPassword("abcdef");
        given()
                .when()
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .post("api/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
