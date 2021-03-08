package pl.tasklist.tasklistbackend.service;

import pl.tasklist.tasklistbackend.dto.TaskDTO;
import pl.tasklist.tasklistbackend.dto.TaskGetDTO;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.exception.ForbiddenException;

import java.util.List;

public interface TaskService {
    Task add(TaskDTO taskDTO);
    Task update(Long id, TaskDTO taskDTO) throws ForbiddenException;
    void delete(Long id) throws ForbiddenException;
    List<TaskGetDTO> getAll();
}
