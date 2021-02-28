package pl.tasklist.tasklistbackend.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.UserAlreadyExistsException;
import pl.tasklist.tasklistbackend.exception.UserDoesNotExistException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class UserRepository {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(User user) throws UserAlreadyExistsException {
        if(doesExist(user.getUsername())){
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    public boolean doesExist(String username) {
        String query = "SELECT count(user) FROM User user WHERE user.username =: username";
        Long count = (Long) entityManager
                .createQuery(query)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }


    public User findByUsername(String username) throws UserDoesNotExistException {
        if(doesExist(username))
            throw new UserDoesNotExistException();
        String query = "SELECT user FROM User user WHERE user.username =: username";
        return entityManager
                .createQuery(query, User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
