package pl.tasklist.tasklistbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tasklist.tasklistbackend.dto.TaskDTO;
import pl.tasklist.tasklistbackend.dto.TaskGetDTO;
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
    public ResponseEntity<?> add(@Valid @RequestBody TaskDTO taskDTO) {
        Task newTask = taskServiceImpl.add(taskDTO);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PostMapping("task/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) throws ForbiddenException {
        Task updatedTask = taskServiceImpl.update(id, taskDTO);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("task/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ForbiddenException {
        taskServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("task/getAll")
    public ResponseEntity<List<TaskGetDTO>> getAll() {
        List<TaskGetDTO> taskGetDTOs = taskServiceImpl.getAll();
        return new ResponseEntity<>(taskGetDTOs, HttpStatus.OK);
    }

}
