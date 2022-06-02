<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- container는 중앙에 위치하며 사이드에 공백을 어는정도 확보하게 해준다. -->
<div class="container">

	<button class="btn btn-secondary" onclick="back()">돌아가기</button>
	<!-- 작성자와 로그인한 사람이 같은 경우만 수정 삭제 가능 -->
	<c:if test="${board.user.id == principal.user.id}">
		<!-- 수정화면으로 이동 url 호출 -> Getmapping으로 처리하기 위해  -->
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br /> <br />
	<div>
		글 번호 : <span id="id"><i>${board.id}</i></span>&nbsp;&nbsp; 작성자 : <span><i>${board.user.username} </i></span>
	</div>
	<br />
	<div>
		<!--  제목 -->
		<h3>${board.title}</h3>
	</div>
	<hr />
	<!-- 선을 그어준다 -->
	<div>
		<!-- 본문 -->
		<div>${board.content}</div>
	</div>
	<hr />

	<!-- 댓글 쓰기 -->
	<div class="card">
		<form>
			<input type="hidden" id="userId" value="${principal.user.id}" />
			<!-- 작성자 -->
			<input type="hidden" id="boardId" value="${board.id}" />
			<!-- 대상 게시글 -->

			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
	</div>
	<br />

	<!-- 댓글 리스트 -->
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply-box" class="list-group">

			<!-- 댓글 쓰기가 성공하면 댓글 리스트에 화면 refresh없이 바로바로 댓글 적은 결과가 리스트로  -->
			<!-- display되도록 스크립트에서 동적으로 html을 바꿔준다. -->
			<!--  board.js의 function addReply(data){} 에서	수행  -->
			<c:forEach var="reply" items="${board.replys}">
			
				<!-- 댓글 아이템  addReply () 함수의 작업 대상 영역 -->
				<li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
					<div>${reply.content}</div>
					<div class="d-flex">
						<div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>

						<!-- 로그인한 사람과 작성자가 동일인이면 삭제 가능 -->
						<c:if test="${reply.user.id eq principal.user.id}">
							<!-- 자바스크립트에 있는 index.init에 등록하지 않고 함수를 바로 호출한다. -->
							<button onClick="index.replyDelete(${board.id}, ${reply.id})" class="badge">삭제</button>
						</c:if>

					</div>
				</li>

			</c:forEach>
		</ul>
	</div>

</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


