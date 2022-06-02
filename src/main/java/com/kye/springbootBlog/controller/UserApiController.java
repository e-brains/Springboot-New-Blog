package com.kye.springbootBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}
