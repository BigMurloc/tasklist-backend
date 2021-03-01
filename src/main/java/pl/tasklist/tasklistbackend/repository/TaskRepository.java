package pl.tasklist.tasklistbackend.repository;

import org.springframework.stereotype.Repository;
import pl.tasklist.tasklistbackend.entity.Task;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TaskRepository {
    private final EntityManager entityManager;

    public TaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Task task) {
        entityManager.persist(task);
    }

    public void update(Task task) {
        entityManager.merge(task);
    }

    public Task findById(Long primaryKey) {
        return entityManager.find(Task.class, primaryKey);
    }

    public void delete(Long primaryKey) {
        entityManager.remove(findById(primaryKey));
    }

    public List<Task> getAll(Long creator) {
        String query = "SELECT task FROM Task task WHERE task.user.id =: creator";
        return entityManager
                .createQuery(query)
                .setParameter("creator", creator)
                .getResultList();
    }
}
