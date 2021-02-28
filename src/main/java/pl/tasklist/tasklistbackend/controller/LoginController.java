package pl.tasklist.tasklistbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.exception.UnauthorizedException;
import pl.tasklist.tasklistbackend.service.AuthenticationService;

@RestController
public class LoginController {


    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public void login(@RequestBody UserLoginDTO userLoginDTO) throws UnauthorizedException {
        if(!authenticationService.login(userLoginDTO))
            throw new UnauthorizedException();
    }


}
