package pl.tasklist.tasklistbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TaskDTO {

    @NotBlank
    private String title;
    private String description;
}
