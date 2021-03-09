package pl.tasklist.tasklistbackend.service;

import pl.tasklist.tasklistbackend.payload.TaskRequest;
import pl.tasklist.tasklistbackend.payload.TaskResponse;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.exception.ForbiddenException;

import java.util.List;

public interface TaskService {
    Task add(TaskRequest taskRequest);
    Task update(Long id, TaskRequest taskRequest) throws ForbiddenException;
    void delete(Long id) throws ForbiddenException;
    List<TaskResponse> getAll();
}
