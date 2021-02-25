package pl.tasklist.tasklistbackend.user;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.dto.UserDTO;
import pl.tasklist.tasklistbackend.entity.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class RegisterIT {

    private final String CORRECT_PASSWORD_EXAMPLE = "aA1!";

    @BeforeClass
    public static void before(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("alreadyExists");
        userDTO.setPassword("alreadyExists");
        given()
            .body(userDTO)
            .contentType(ContentType.JSON)
            .post("api/user/register");
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
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void when_user_already_exists_then_CONFLICT(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("alreadyExists");
        userDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.CONFLICT.value())
            .and()
                .content(equalTo("User already exists!"));
    }

    @Test
    public void when_username_is_blank_then_BAD_REQUEST(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("");
        userDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void when_username_is_less_than_4_characters_long_then_BAD_REQUEST(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("tes");
        userDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_username_is_longer_than_16_characters_then_BAD_REQUEST(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Longerthan16charactersforsure");
        userDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
            .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_password_does_not_match_regex_then_BAD_REQUEST(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("tester!");
        userDTO.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
                .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("/api/user/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_password_is_blank_then_BAD_REQUEST(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("");
        given()
                .when()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .post("api/user/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
