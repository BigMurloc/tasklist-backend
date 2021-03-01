package pl.tasklist.tasklistbackend.repository;

import org.springframework.stereotype.Repository;
import pl.tasklist.tasklistbackend.entity.Task;

import java.util.List;

@Repository
public class TaskRepository {
    public void save(Task task) {
    }

    public void update(Task task) {
    }

    public Task findById(Long id) {
        return null;
    }

    public void delete(Long id) {
    }

    public List<Task> getAll() {
        return null;
    }
}
