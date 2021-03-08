package pl.tasklist.tasklistbackend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserAlreadyExistsException;
import pl.tasklist.tasklistbackend.exception.UserDoesNotExistException;
import pl.tasklist.tasklistbackend.repository.UserRepository;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void register(User user) throws UserAlreadyExistsException {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
