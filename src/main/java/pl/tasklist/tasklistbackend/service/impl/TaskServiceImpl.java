package pl.tasklist.tasklistbackend.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tasklist.tasklistbackend.payload.TaskRequest;
import pl.tasklist.tasklistbackend.payload.TaskResponse;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.entity.User;
import pl.tasklist.tasklistbackend.exception.ForbiddenException;
import pl.tasklist.tasklistbackend.repository.TaskRepository;
import pl.tasklist.tasklistbackend.service.TaskService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(ModelMapper modelMapper,
                           TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    public Task add(TaskRequest taskRequest) {
        Task task = convertToEntity(taskRequest);
        User currentUser = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        task.setUser(currentUser);
        task.setTimestamp(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS));
        return taskRepository.save(task);
    }

    public Task update(Long id, TaskRequest taskRequest) throws ForbiddenException {
        Task task = taskRepository.findById(id).orElseThrow();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!currentUser.equals(task.getUser()))
            throw new ForbiddenException();
        modelMapper.map(taskRequest, task);
        return taskRepository.save(task);
    }

    public void delete(Long id) throws ForbiddenException {
        Task task = taskRepository.findById(id).orElseThrow();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!currentUser.equals(task.getUser()))
            throw new ForbiddenException();
        taskRepository.delete(task);
    }

    public List<TaskResponse> getAll() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Task> tasks = taskRepository.findByUserId(currentUser.getId());
        return tasks
                .stream()
                .map((task) -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }

    private Task convertToEntity(TaskRequest taskRequest){
        Task task = modelMapper.map(taskRequest, Task.class);
        return task;
    }
}
