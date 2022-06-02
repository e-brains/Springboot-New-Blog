package com.kye.springbootBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kye.springbootBlog.auth.PrincipalDetail;
import com.kye.springbootBlog.common.ReplySaveRequestDto;
import com.kye.springbootBlog.common.ResponseDto;
import com.kye.springbootBlog.model.Board;
import com.kye.springbootBlog.model.Reply;
import com.kye.springbootBlog.service.BoardService;


@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	/* 글쓰기 */
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	/* 글 삭제 */
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable long id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	/* 글 수정 */
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable long id, @RequestBody Board board){		
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	/* 댓글 쓰기  */
	// 여러 유형의 데이터를 다뤄야 하는 경우 dto를 만들어서 받는게 좋다.	 
	// NoRefresh처리를 위해 리턴값 수정
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Reply> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		Reply result = boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Reply>(HttpStatus.OK.value(), result); 
	}
		
	/* 댓글 삭제 */
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}") // boardId는 단순히 주소를 만들기 위해 사용
	public ResponseDto<Integer> replyDelete(@PathVariable long replyId) {
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
}



