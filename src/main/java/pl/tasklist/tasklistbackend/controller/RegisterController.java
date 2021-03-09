package pl.tasklist.tasklistbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tasklist.tasklistbackend.payload.RegisterRequest;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserAlreadyExistsException;
import pl.tasklist.tasklistbackend.service.impl.UserServiceImpl;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    public RegisterController(UserServiceImpl userServiceImpl, ModelMapper modelMapper) {
        this.userServiceImpl = userServiceImpl;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody RegisterRequest registerRequest) throws UserAlreadyExistsException {
        User newUser = userServiceImpl.register(convertToEntity(registerRequest));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    private User convertToEntity(RegisterRequest registerRequest){
        User user = modelMapper.map(registerRequest, User.class);
        return user;
    }
}
