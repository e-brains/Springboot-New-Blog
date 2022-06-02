package com.kye.springbootBlog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kye.springbootBlog.common.RoleType;
import com.kye.springbootBlog.model.User;
import com.kye.springbootBlog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public BCryptPasswordEncoder encoder;	
	
	
	@Transactional
	public int 회원가입(User user) {
		 
		String password = encoder.encode(user.getPassword()); // 해쉬
		user.setPassword(password);		
		user.setRole(RoleType.USER);
		
		try {
			userRepository.save(user);
			System.out.println("회원가입 성공 저장성공");
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원가입 실패 저장실패  ==> " + e.getMessage());			
			return -1;
		}
		
	}
	
	// select 할 때 트랜잭션이 진행되는 동안 정합성 보장을 위해 @Transactional 옵션을 준다.
	@Transactional(readOnly = true) 
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{  
			return new User();
		});
		return user;
	}
	
	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});		
	
		String encPassword = encoder.encode(user.getPassword());
		persistance.setPassword(encPassword);
		persistance.setAddress(user.getAddress());
		
		// Validate 체크 => oauth 필드에 값이 없으면 수정 가능
//		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
//			String rawPassword = user.getPassword();
//			String encPassword = encoder.encode(rawPassword);
//			persistance.setPassword(encPassword);
//			persistance.setEmail(user.getEmail());
//		}
		
		// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됩니다.
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
	}
	
}
