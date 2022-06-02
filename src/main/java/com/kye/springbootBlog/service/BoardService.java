package com.kye.springbootBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kye.springbootBlog.common.ReplySaveRequestDto;
import com.kye.springbootBlog.model.Board;
import com.kye.springbootBlog.model.Reply;
import com.kye.springbootBlog.model.User;
import com.kye.springbootBlog.repository.BoardRepository;
import com.kye.springbootBlog.repository.ReplyRepository;
import com.kye.springbootBlog.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
//final같은 초기화가 필요한 필드를 파라미터 생성자를 통해서 DI 및 초기화 하라는 의미
@RequiredArgsConstructor  
public class BoardService {
	
	/* final 선언과 @RequiredArgsConstructor를 이용한 DI 및 초기화 */
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;	
	
	/* 원래 DI의 모습 - 이게 귚찮아서 @Autowired를 사용 */
//	private  BoardRepository boardRepository;
//	private  ReplyRepository replyRepository;	
//	public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
//		this.boardRepository = bRepo;
//		this.replyRepository = rRepo;
//	}
	
	/* Autowired를 통한 DI 방식 */
//	@Autowired
//	private BoardRepository boardRepository;
//	
//	@Autowired
//	private ReplyRepository replyRepository;
//	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		//board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(long id) {
		return boardRepository.findById(id) // board는 reply를 들고 있기 때문에 댓글까지 같이 조회한다.
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(long id) {	
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(long id, Board requestBoard) {
		// 먼저 해당 id로 저장된 기존 데이터를 읽는다.
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		// 기존 데이터에 변경된 요소만 다시 저장
		board.setTitle(requestBoard.getTitle());   
		board.setContent(requestBoard.getContent());  
		// 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트가 됨. db flush
	}
	
	// Repository에 직접 네이티브 쿼리를 만들어서 처리하는 방법
//	@Transactional
//	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
//		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
//		System.out.println("result ====" + result);
//	}
	
	/* 댓글 쓰기  - JPA의 기본 save함수를 이용하는 방법 : 번거롭다. */
	//  JPA의 기본 save함수를 이용하기 위해서는 user와 board의 영속화 작업이 필요한다.
	// NoRefresh 처리를 위해 msave포기하고 save 사용한다. 저장된 reply정보를 얻기 위해
	@Transactional
	public Reply 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
		// user 영속화 
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글쓰기 실패 : 유저 id를 찾을 수 없습니다.");
		});
		
		// board 영속
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
		});
		
		// reply 오브젝트 입력
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
		
		// JPA Repository save() 기본 함수 호출
		Reply result = replyRepository.save(reply);		
		
		return result;
		
	}
		
	@Transactional
	public void 댓글삭제(long replyId) {
		replyRepository.deleteById(replyId);
	}
	
}
