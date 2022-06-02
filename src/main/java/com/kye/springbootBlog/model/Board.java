package com.kye.springbootBlog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB전략에 따른다.
	private long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터 처리
	private String content;  // summernote 라이브러리 사용하면 용량이 크다
	
	@ColumnDefault("0") // 문자면 홑따옴표 ' ' 넣어줘야 하나 정수는 숫자만 입력
	private int count;  // 조회수
	
	// DB는 조인을 위해 Foreign key를 사용하지만
	// ORM은 오브젝트를 사용하기 때문에 관련된 오브젝트를 
	// 그대로 설정하면 되는데 대신 어노테이션을 이용해 어떤 
	// 항목이 Foreign key로 사용되는지 알려줘야 한다.		
	
	// 연관관계도 설정 (many = board, user = one)
	// EAGER 는 default,  한건 밖에 안되니 조회시 바로 가져온다.
	@ManyToOne(fetch = FetchType.EAGER) 
	// 테이블이 생성될때 userId의 이름으로  Foreign key 생성
	@JoinColumn(name = "userId") 
	private User user;
	// private int userId;  // 이런식으로 사용하지 않는다	
	
	// 하나의 게시글에 여러개의 댓글
	// 이때에는 replyId라는 Foreign key를 생성할 수 없다.
	// board가 1 row 이기 때문에 replyId때문에 여러개 row생성이 불가하기 때문
	// 이때에는 조인하는 것이 아니라 단순히 boardId를 가지고 reply테이블에서
	// 조회만 하도록 요청한다.
	// 즉, mappedBy는 연관관계의 주인이 아니니 테이블 생성시 replyId라는  
	// Foreign key 컬럼을 생성하지 말고 나중에 조회 시
	// reply 모델에 있는 board 속성(객체)을 참조하여 reply의 데이터를 조회하라는 의미
	// reply.java의 Board타입의 변수 board 를 입력한다.
	// fetch 전략은 EAGER전략으로 같이 조회되도록 한다. 
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) 
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")  // 최신 글 부터
	private List<Reply> replys;  //  복수개의 댓글을 담아야 함으로
	
	// cascade값 주기 : 게시글을 삭제할 때 하위에 있는 댓글들을 어떻게 할건지 정의
	// CascadeType.REMOVE는 모두 삭제 의미 
	
	// 무한 참조 방지
	// reply 모델안에 board가 있고 board안에 reply가 있어서 jackson에 의해 무한참조가
	// 일어난다.
	// @JsonIgnoreProperties({"board"})를 걸면 reply안의 board는 무시한다.
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
