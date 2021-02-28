package pl.tasklist.tasklistbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 4, max = 16)
    @Pattern(regexp = "^[A-z]\\w+", message = "Username must contain of letters and digits only!")
    private String username;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 4, max = 16)
    private String password;

}