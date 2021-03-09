package pl.tasklist.tasklistbackend.task;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tasklist.tasklistbackend.IntegrationTest;
import pl.tasklist.tasklistbackend.payload.TaskRequest;
import pl.tasklist.tasklistbackend.payload.LoginRequest;
import pl.tasklist.tasklistbackend.payload.RegisterRequest;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserDoesNotExistException;
import pl.tasklist.tasklistbackend.repository.TaskRepositoryOld;
import pl.tasklist.tasklistbackend.repository.UserRepositoryOld;

import java.util.List;

import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@IntegrationTest
public class TaskIT {

    private static final String CORRECT_PASSWORD_EXAMPLE = "Aa!1";
    private static Response ownerResponse;
    private static Response authUserResponse;
    @Autowired
    private TaskRepositoryOld taskRepositoryOld;
    @Autowired
    private UserRepositoryOld userRepositoryOld;

    @BeforeClass
    public static void setUp(){
        registerUsers();
        loginUsers();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        given()
                .cookies(ownerResponse.getCookies())
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/add");
    }

    private static void loginUsers() {
        LoginRequest ownerLogin = new LoginRequest();
        ownerLogin.setUsername("owner");
        ownerLogin.setPassword(CORRECT_PASSWORD_EXAMPLE);
        ownerResponse = given()
                .body(ownerLogin)
                .contentType(ContentType.JSON)
                .post("/api/login")
                .thenReturn();
        LoginRequest authenticatedUserLogin = new LoginRequest();
        authenticatedUserLogin.setUsername("authUser");
        authenticatedUserLogin.setPassword(CORRECT_PASSWORD_EXAMPLE);
        authUserResponse = given()
                .body(authenticatedUserLogin)
                .contentType(ContentType.JSON)
                .post("/api/login")
                .thenReturn();
    }

    private static void registerUsers() {
        RegisterRequest owner = new RegisterRequest();
        owner.setUsername("owner");
        owner.setPassword(CORRECT_PASSWORD_EXAMPLE);
        RegisterRequest authenticatedUser = new RegisterRequest();
        authenticatedUser.setUsername("authUser");
        authenticatedUser.setPassword(CORRECT_PASSWORD_EXAMPLE);
        given()
                .body(owner)
                .contentType(ContentType.JSON)
                .post("/api/register");
        given()
                .body(authenticatedUser)
                .contentType(ContentType.JSON)
                .post("/api/register");
    }


    @Test
    public void when_auth_user_creates_task_then_CREATED() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        given()
                .when()
                .cookies(authUserResponse.getCookies())
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/add")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void when_unauthenticated_user_creates_task_then_UNAUTHORIZED() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        given()
                .when()
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/add")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void when_owner_updates_task_then_OK() throws UserDoesNotExistException {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test123");
        taskRequest.setDescription("test");
        User owner = userRepositoryOld.findByUsername("owner");
        List<Task> tasks = taskRepositoryOld.getAll(owner.getId());
        given()
                .cookies(ownerResponse.getCookies())
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/update/" + tasks.get(0).getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void when_unauthenticated_user_updates_owners_task_then_UNAUTHORIZED() throws UserDoesNotExistException {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test123");
        taskRequest.setDescription("test");
        User owner = userRepositoryOld.findByUsername("owner");
        List<Task> tasks = taskRepositoryOld.getAll(owner.getId());
        given()
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/update/" + tasks.get(0).getId())
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void when_auth_user_updates_owners_task_then_FORBIDDEN() throws UserDoesNotExistException {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test123");
        taskRequest.setDescription("test");
        User owner = userRepositoryOld.findByUsername("owner");
        List<Task> tasks = taskRepositoryOld.getAll(owner.getId());
        given()
            .cookies(authUserResponse.getCookies())
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/update/" + tasks.get(0).getId())
            .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void when_owner_deletes_task_then_OK() throws UserDoesNotExistException {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("delete");
        given()
                .cookies(ownerResponse.getCookies())
                .body(taskRequest)
                .contentType(ContentType.JSON)
                .post("/api/task/add");
        User owner = userRepositoryOld.findByUsername("owner");
        List<Task> tasks = taskRepositoryOld.getAll(owner.getId());
        Task taskToBeDeleted = tasks.stream()
                .filter((task) -> task.getTitle().equals("delete"))
                .findFirst().get();
        given()
                .cookies(ownerResponse.getCookies())
                .delete("/api/task/delete/"+taskToBeDeleted.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void when_auth_user_deletes_owners_task_then_FORBIDDEN() throws UserDoesNotExistException {
        User owner = userRepositoryOld.findByUsername("owner");
        List<Task> tasks = taskRepositoryOld.getAll(owner.getId());
        given()
            .cookies(authUserResponse.getCookies())
                .delete("/api/task/delete/" + tasks.stream().findAny().get().getId())
            .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void when_unauthenticated_user_deletes_owners_task_then_UNAUTHORIZED() throws UserDoesNotExistException {
        User owner = userRepositoryOld.findByUsername("owner");
        List<Task> tasks = taskRepositoryOld.getAll(owner.getId());
        given()
                .post("/api/task/delete/" + tasks.stream().findAny().get().getId())
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void when_owner_gets_tasks_then_OK() {
        given()
            .cookies(ownerResponse.getCookies())
                .get("/api/task/getAll")
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void when_unauthenticated_user_gets_owners_task_then_UNAUTHORIZED() {
        given()
                .get("/api/task/getAll")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
