package com.kye.springbootBlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kye.springbootBlog.auth.PrincipalDetailService;



// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@SuppressWarnings("deprecation")
@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
//Controller에서 특정 권한이 있는 유저만 접근을 허용하려면 @PreAuthorize 어노테이션을 사용하는데, 
//해당 어노테이션을 활성화 시키는 어노테이션이다.
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
		
	// 회원정보 수정시 스프링 시큐리티에 개입해서 시큐리티 세션의 회원정보를 
	// 수정하기 위해서 AuthenticationManager 를 빈에 등록한다.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // IoC가 되요!!
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음. 
	// principalDetailService에게 암호화 방식을 알려줘야 함
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") 
				.permitAll()   // antMatchers에서 설정한 경로는 모두 열어 놓는다.
				.anyRequest()  // 이외 다른 요청들은
				.authenticated()  // 인증이 되어야 함
			.and()
				.formLogin()  // 로그인폼 설정
				.loginPage("/auth/loginForm")  // 내가 만든 로그인 페이지로 보낸다.
				.loginProcessingUrl("/auth/loginProc")  // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
				.defaultSuccessUrl("/"); // 로그인이 성공하면 보내는 곳
				//.failureUrl("/fail");  // 로그인이 실패했을때 보내는 곳
	}
}
