package com.example.semiprojectjpa.entity;

import com.example.semiprojectjpa.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String username;

    @Column
    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public MemberDto toDto() {
        return MemberDto.builder()
                .username(username)
                .password("")
                .name(name)
                .email(email)
                .checkpw("")
                .build();
    }
}

