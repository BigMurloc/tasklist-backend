package pl.tasklist.tasklistbackend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.config.AppAuthentication;
import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UnauthorizedException;
import pl.tasklist.tasklistbackend.exception.UserDoesNotExistException;
import pl.tasklist.tasklistbackend.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;

    public AuthenticationService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public boolean login(UserLoginDTO userLoginDTO) throws UnauthorizedException {
        User user;
        try {
            user = userRepository.findByUsername(userLoginDTO.getUsername());
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            throw new UnauthorizedException();
        }
        if(userService.matches(userLoginDTO.getPassword(), user.getPassword())){
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
            return true;
        }
        return false;
    }
}
