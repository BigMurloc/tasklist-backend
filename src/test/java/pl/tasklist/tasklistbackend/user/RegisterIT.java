package pl.tasklist.tasklistbackend.user;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.dto.UserRegisterDTO;
import pl.tasklist.tasklistbackend.entity.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class RegisterIT {

    private final String CORRECT_PASSWORD_EXAMPLE = "aA1!";

    @BeforeClass
    public static void before(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("alreadyExists");
        userRegisterDTO.setPassword("alreadyExists");
        given()
            .body(userRegisterDTO)
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
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("alreadyExists");
        userRegisterDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.CONFLICT.value())
            .and()
                .content(equalTo("User already exists!"));
    }

    @Test
    public void when_username_is_blank_then_BAD_REQUEST(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("");
        userRegisterDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void when_username_is_less_than_4_characters_long_then_BAD_REQUEST(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("tes");
        userRegisterDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_username_is_longer_than_16_characters_then_BAD_REQUEST(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("Longerthan16charactersforsure");
        userRegisterDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("api/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_username_does_not_match_regex_then_BAD_REQUEST(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("tester!");
        userRegisterDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
                .when()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_password_is_blank_then_BAD_REQUEST(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("test");
        userRegisterDTO.setPassword("");
        given()
                .when()
                .body(userRegisterDTO)
                .contentType(ContentType.JSON)
                .post("api/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
