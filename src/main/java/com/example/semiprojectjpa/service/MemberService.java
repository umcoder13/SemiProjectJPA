package com.example.semiprojectjpa.service;

import com.example.semiprojectjpa.dto.MemberDto;
import com.example.semiprojectjpa.entity.Member;

import java.util.Optional;

public interface MemberService {
    void join(MemberDto dto);

    boolean checkId(String username);

    Optional<Member> findByUsername(String username);

    void update(MemberDto dto);

    String findPw(String username);

}
