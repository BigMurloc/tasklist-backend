package pl.tasklist.tasklistbackend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserAlreadyExistsException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EntityManager entityManager;

    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(User user) throws UserAlreadyExistsException {
        Long count = doesExist(user.getUsername());
        if(count != 0){
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    private Long doesExist(String username) {
        String query = "SELECT count(user) FROM User user WHERE user.username =: username";
        Long count = (Long) entityManager
                .createQuery(query)
                .setParameter("username", username)
                .getSingleResult();
        return count;
    }


}
