package pl.tasklist.tasklistbackend.service;

import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.exception.UnauthorizedException;

public interface AuthenticationService {
    boolean login(UserLoginDTO userLoginDTO) throws UnauthorizedException;
}
