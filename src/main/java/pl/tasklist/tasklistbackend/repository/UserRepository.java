package pl.tasklist.tasklistbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tasklist.tasklistbackend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
