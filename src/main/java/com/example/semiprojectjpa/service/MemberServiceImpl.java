package com.example.semiprojectjpa.service;

import com.example.semiprojectjpa.dto.MemberDto;
import com.example.semiprojectjpa.entity.Member;

import com.example.semiprojectjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    @Override
    public void join(MemberDto dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        memberRepository.saveAndFlush(dto.toEntity());
    }

    @Transactional
    @Override
    public void update(MemberDto dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        Optional<Member> memberOptional = memberRepository.findByUsername(dto.getUsername());
        if(memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setName(dto.getName());
            member.setPassword(dto.getPassword());
            member.setEmail(dto.getEmail());
            memberRepository.save(member);
        }
    }

    @Override
    public boolean checkId(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.isPresent();
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    @NotBlank
    public String findPw(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if(member.isPresent()) {
            Member mb = member.get();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            uuid = uuid.substring(0, 10);
            mb.setPassword(encoder.encode(uuid));
            memberRepository.saveAndFlush(mb);
            return uuid;
        } else {
            String pw = "아이디가 없습니다!";
            return pw;
        }

    }


}
