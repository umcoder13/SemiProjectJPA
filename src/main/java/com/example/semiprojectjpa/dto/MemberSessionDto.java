package com.example.semiprojectjpa.dto;

import com.example.semiprojectjpa.entity.Member;
import com.example.semiprojectjpa.entity.UserRole;

import java.io.Serializable;

public class MemberSessionDto implements Serializable {
    private String username;

    private String password;

    private String name;

    private String email;

    private UserRole role;

    public MemberSessionDto(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}
