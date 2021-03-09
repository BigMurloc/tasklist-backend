package pl.tasklist.tasklistbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tasklist.tasklistbackend.payload.TaskRequest;
import pl.tasklist.tasklistbackend.payload.TaskResponse;
import pl.tasklist.tasklistbackend.entity.Task;
import pl.tasklist.tasklistbackend.exception.ForbiddenException;
import pl.tasklist.tasklistbackend.service.impl.TaskServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping("task/add")
    public ResponseEntity<?> add(@Valid @RequestBody TaskRequest taskRequest) {
        Task newTask = taskServiceImpl.add(taskRequest);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PostMapping("task/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TaskRequest taskRequest) throws ForbiddenException {
        Task updatedTask = taskServiceImpl.update(id, taskRequest);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("task/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ForbiddenException {
        taskServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("task/getAll")
    public ResponseEntity<List<TaskResponse>> getAll() {
        List<TaskResponse> taskResponses = taskServiceImpl.getAll();
        return new ResponseEntity<>(taskResponses, HttpStatus.OK);
    }

}
