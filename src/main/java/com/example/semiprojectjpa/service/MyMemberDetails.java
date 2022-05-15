package com.example.semiprojectjpa.service;

import com.example.semiprojectjpa.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class MyMemberDetails implements UserDetails {

    private Member member;

    @Override
    @NotBlank
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    @NotNull
    public String getPassword() {
        return member.getPassword();
    }

    public String getName() { return member.getName(); }

    public Long getId() { return member.getId(); }

    /* 계정 만료 여부 * true : 만료 안됨 * false : 만료 */
    @Override public boolean isAccountNonExpired() { return true; }
    /* 계정 잠김 여부 * true : 잠기지 않음 * false : 잠김 */
    @Override public boolean isAccountNonLocked() { return true; }
    /* 비밀번호 만료 여부 * true : 만료 안됨 * false : 만료 */
    @Override public boolean isCredentialsNonExpired() { return true; }
    /* 사용자 활성화 여부 * true : 만료 안됨 * false : 만료 */
    @Override public boolean isEnabled() { return true; }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(() -> "ROLE_" + member.getRole());

        return collection;
    }
}
