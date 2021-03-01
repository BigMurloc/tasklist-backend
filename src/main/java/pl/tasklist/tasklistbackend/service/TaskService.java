package pl.tasklist.tasklistbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.dto.TaskDTO;
import pl.tasklist.tasklistbackend.dto.TaskGetDTO;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
        task.setTimestamp(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS));
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

    public List<TaskGetDTO> getAll() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Task> tasks = taskRepository.getAll(currentUser.getId());
        return tasks
                .stream()
                .map((task) -> modelMapper.map(task, TaskGetDTO.class))
                .collect(Collectors.toList());
    }

    private Task convertToEntity(TaskDTO taskDTO){
        Task task = modelMapper.map(taskDTO, Task.class);
        return task;
    }
}
