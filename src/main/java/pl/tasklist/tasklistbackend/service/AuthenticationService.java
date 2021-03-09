package pl.tasklist.tasklistbackend.service;

import pl.tasklist.tasklistbackend.payload.LoginRequest;
import pl.tasklist.tasklistbackend.exception.UnauthorizedException;

public interface AuthenticationService {
    boolean login(LoginRequest loginRequest) throws UnauthorizedException;
}
