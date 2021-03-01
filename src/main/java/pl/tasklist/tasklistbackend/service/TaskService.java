package pl.tasklist.tasklistbackend.service;

import org.modelmapper.ModelMapper;
import pl.tasklist.tasklistbackend.dto.TaskDTO;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.repository.TaskRepository;

import java.util.List;

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
        taskRepository.update(task);
    }

    public void delete(Long id) {
        taskRepository.delete(id);
    }

    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    private Task convertToEntity(TaskDTO taskDTO){
        Task task = modelMapper.map(taskDTO, Task.class);
        return task;
    }
}
