package pl.tasklist.tasklistbackend.user;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.entity.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class RegisterIT {

    @BeforeClass
    public static void before(){
        User user = new User();
        user.setUsername("alreadyExists");
        user.setPassword("alreadyExists");
        given()
            .body(user)
            .contentType(ContentType.JSON)
            .post("api/user/register");
    }

    @Test
    public void when_new_user_then_CREATED(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        given()
            .when()
                .body(user)
                .contentType(ContentType.JSON)
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void when_user_already_exists_then_CONFLICT(){
        User user = new User();
        user.setUsername("alreadyExists");
        user.setPassword("alreadyExists");
        given()
            .when()
                .body(user)
                .contentType(ContentType.JSON)
                .post("api/user/register")
            .then()
                .statusCode(HttpStatus.CONFLICT.value())
            .and()
                .content(equalTo("User already exists!"));
    }


}
