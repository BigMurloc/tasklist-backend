package pl.tasklist.tasklistbackend.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public boolean matches(String rawPassword, String hashedPassword) {
        return false;
    }
}
