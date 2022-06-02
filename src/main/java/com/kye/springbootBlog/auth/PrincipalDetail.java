package com.kye.springbootBlog.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kye.springbootBlog.model.User;

import lombok.Data;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Data
public class PrincipalDetail implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private User user; // 컴포지션 (클래스가 객체를 들고 있는 경우) 

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

// 계정이 만료되지 않았는지 리턴한다. (true: 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

// 계정이 잠겨있지 않았는지 리턴한다. (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

// 비밀번호가 만료되지 않았는지 리턴한다. (true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

// 계정이 활성화(사용가능)인지 리턴한다. (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

// 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<>();
		
		// 자바는 메서드를 파라미터로 쓸수 없지만 오브젝트는 가능하다.
//		collectors.add(new GrantedAuthority() {			
//			@Override
//			public String getAuthority() {				
//				return "ROLE_" + user.getRole();
//			}
//		});
		// 자바 1.8부터 메서드가 하나밖에 없을 때는 람다식을 쓸 수 있다.
		collectors.add(() -> {
			return "ROLE_" + user.getRole();
		});

		return collectors;
	}

}