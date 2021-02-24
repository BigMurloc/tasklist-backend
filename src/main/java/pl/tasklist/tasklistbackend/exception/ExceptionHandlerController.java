package pl.tasklist.tasklistbackend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserAlreadyExists(RuntimeException exception, WebRequest webRequest){
        String bodyOfResponse = "User already exists!";
        return handleExceptionInternal(
                exception,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                webRequest
        );
    }


}
