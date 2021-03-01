package pl.tasklist.tasklistbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskGetDTO {
    private String id;
    private String title;
    private String description;
    private String timestamp;
}
