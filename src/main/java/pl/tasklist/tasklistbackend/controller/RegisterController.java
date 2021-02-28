package pl.tasklist.tasklistbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tasklist.tasklistbackend.dto.UserRegisterDTO;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserAlreadyExistsException;
import pl.tasklist.tasklistbackend.service.UserService;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public RegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> save(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws UserAlreadyExistsException {
        userService.save(convertToEntity(userRegisterDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private User convertToEntity(UserRegisterDTO userRegisterDTO){
        User user = modelMapper.map(userRegisterDTO, User.class);
        return user;
    }
}
