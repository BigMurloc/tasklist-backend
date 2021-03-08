package pl.tasklist.tasklistbackend.repository;

import org.springframework.stereotype.Repository;
import pl.tasklist.tasklistbackend.entity.Task;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Deprecated
public class TaskRepositoryOld {
    private final EntityManager entityManager;

    public TaskRepositoryOld(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void update(Task task) {
        entityManager.merge(task);
    }

    public Task findById(Long primaryKey) {
        return entityManager.find(Task.class, primaryKey);
    }

    @Transactional
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
