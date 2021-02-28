package pl.tasklist.tasklistbackend.service;

import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.dto.UserLoginDTO;
import pl.tasklist.tasklistbackend.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        return false;
    }
}
