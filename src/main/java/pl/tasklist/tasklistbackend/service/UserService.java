package pl.tasklist.tasklistbackend.service;

import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserAlreadyExistsException;

public interface UserService {
    boolean matches(String rawPassword, String encodedPassword);
    User register(User user) throws UserAlreadyExistsException;
}
