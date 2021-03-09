package pl.tasklist.tasklistbackend.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TaskRequest {

    @NotBlank
    private String title;
    private String description;
}
