package pl.tasklist.tasklistbackend.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.config.AppAuthentication;
import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UnauthorizedException;
import pl.tasklist.tasklistbackend.repository.UserRepository;
import pl.tasklist.tasklistbackend.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     UserServiceImpl userServiceImpl) {
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
    }

    public boolean login(UserLoginDTO userLoginDTO) throws UnauthorizedException {
        User user;
        user = userRepository.findByUsername(userLoginDTO.getUsername());
        if(user == null){
            throw new UnauthorizedException();
        }
        if(userServiceImpl.matches(userLoginDTO.getPassword(), user.getPassword())){
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
            return true;
        }
        return false;
    }
}