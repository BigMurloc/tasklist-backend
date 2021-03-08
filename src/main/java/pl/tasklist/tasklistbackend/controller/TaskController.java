package pl.tasklist.tasklistbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tasklist.tasklistbackend.dto.TaskDTO;
import pl.tasklist.tasklistbackend.dto.TaskGetDTO;
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
    public ResponseEntity<?> add( @Valid @RequestBody TaskDTO taskDTO){
        taskServiceImpl.add(taskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("task/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) throws ForbiddenException {
        taskServiceImpl.update(id, taskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("task/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ForbiddenException {
        taskServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("task/getAll")
    public List<TaskGetDTO> getAll(){
        return taskServiceImpl.getAll();
    }

}
