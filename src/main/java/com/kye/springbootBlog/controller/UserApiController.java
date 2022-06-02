package com.kye.springbootBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kye.springbootBlog.common.ResponseDto;
import com.kye.springbootBlog.model.User;
import com.kye.springbootBlog.service.UserService;

@RestController
public class UserApiController {

	@Autowired // @Autowired로 DI해서 사용하면 됨
	private UserService userService;

	// SecurityConfiguration에서 빈에 등록했던 것을 여기서 DI에서 사용한다.
	@Autowired
	private AuthenticationManager authenticationManager;

	/* 회원가입 수행 */
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, address

		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
	}

	/* 회원정보 수정 */
	@PutMapping("/api/userUpdate")
	public ResponseDto<Integer> update(@RequestBody User user) {

		userService.회원수정(user);

//		System.out.println("user.getUsername == " + user.getUsername());
//		System.out.println("user.getPassword == " + user.getPassword());
//		System.out.println("user.getAddress == " + user.getAddress());
		
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
		// 시큐리티 세션 등록을 위해 새로 변경된 username과 password로 다시 로그인 절차를 밟는다.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		// 위에서 로그인 된 후 만들어진 Authentication을 이용하여 세션을 수정한다.
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}
