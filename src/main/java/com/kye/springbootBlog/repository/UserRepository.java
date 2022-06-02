package com.kye.springbootBlog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kye.springbootBlog.model.User;

//DAO에 해당
//자동으로 bean등록이 된다.
//@Repository // 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Long> {

	// 유저가 존재하는지 체크
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);

	// JPA Naming 쿼리 -> findByUsernameAndPassword
	// SELECT * FROM user WHERE username = ?1 AND password = ?2;
	//User findByUsernameAndPassword(String username, String password);

	// 네이티브 쿼리를 이용할 수도 있다.
	// @Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2",
	// nativeQuery = true)
	// User login(String username, String password);

}
