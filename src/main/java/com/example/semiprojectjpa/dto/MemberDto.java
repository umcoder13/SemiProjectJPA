package com.example.semiprojectjpa.dto;

import com.example.semiprojectjpa.entity.Member;
import com.example.semiprojectjpa.entity.UserRole;
import lombok.*;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class MemberDto {
    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String username;

    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String checkpw;
    
    private boolean checkid = false;

    private UserRole role;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .role(role.USER)
                .build();
    }
}
