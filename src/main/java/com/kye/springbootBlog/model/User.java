package com.kye.springbootBlog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.kye.springbootBlog.common.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id // primary key가 됨
	//  application.yml >> jpa: >> use-new-id-generator-mappings: false로 설정하면 
	// jpa의 넘버링 전략을 따르지 않겠다는 것이며 true이면 jpa의 넘버링 전략을 우선한다는 의미
	// 우리는 false로 설정하였기 때문에 여기서 DB의 전략을 따라도 무방함
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 넘버링 전략을 따라간다
	private long id;  // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true) 
	private String username;  // 아이디
	
	@Column(nullable = false, length = 100)  // 해쉬 암호화 때문에 100 이상 잡는다.
	private String password;
	
	@Column(length = 100)
	private String address;
	
	//DB는 RoleType이라는 것이 없기 때문에 아래 @Enumerated를 붙여서 
	// 해당 enum이 string이라는 것을 알려준다.
	@Enumerated(EnumType.STRING)  
	private RoleType  role;  
	
	
	// 내가 직접 시간을 넣으려면 Timestamp.valueOf(LocalDateTime.now())
	//	 쿼리에서 직접 넣으려면 now()함수를 사용해도 된다.
	@CreationTimestamp // 스프링에서 시간을 넣어준다. 
	private Timestamp createDate;
}
