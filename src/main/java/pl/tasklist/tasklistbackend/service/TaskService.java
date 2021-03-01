package pl.tasklist.tasklistbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.dto.TaskDTO;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    public TaskService(ModelMapper modelMapper, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    public void add(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        taskRepository.save(task);
    }

    public void update(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id);
        modelMapper.map(taskDTO, task);
        taskRepository.update(task);
    }

    public void delete(Long id) {
        taskRepository.delete(id);
    }

    public List<Task> getAll() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskRepository.getAll(currentUser.getId());
    }

    private Task convertToEntity(TaskDTO taskDTO){
        Task task = modelMapper.map(taskDTO, Task.class);
        return task;
    }
}