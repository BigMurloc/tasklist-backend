package pl.tasklist.tasklistbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.exception.UnauthorizedException;
import pl.tasklist.tasklistbackend.service.impl.AuthenticationServiceImpl;

@RestController
public class LoginController {


    private final AuthenticationServiceImpl authenticationServiceImpl;

    public LoginController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/login")
    public void login(@RequestBody UserLoginDTO userLoginDTO) throws UnauthorizedException {
        if(!authenticationServiceImpl.login(userLoginDTO))
            throw new UnauthorizedException();
    }


}
