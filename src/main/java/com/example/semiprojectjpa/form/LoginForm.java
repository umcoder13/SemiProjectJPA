package com.example.semiprojectjpa.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class LoginForm {
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank
    private String username;

    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank
    private String password;
}
