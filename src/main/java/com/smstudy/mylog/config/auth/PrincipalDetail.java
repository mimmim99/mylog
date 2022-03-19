package com.smstudy.mylog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smstudy.mylog.member.entity.Member;

import lombok.Getter;

@Getter
public class PrincipalDetail implements UserDetails {
	
	Member member;
	
	public PrincipalDetail(Member member) {
		this.member = member;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다. (true : 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겼는지 확인 (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료 됐는지 확인 (true : 만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능)인지 리턴한다 (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		
		collectors.add(()->{
			return "ROLE_" + member.getRole();
		});
		
		return collectors;
	}
}
