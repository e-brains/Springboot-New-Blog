let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();  //delete가 예약어여서 ById를 붙임
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
	},

	/*글쓰기*/
	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	/*글삭제*/
	deleteById: function() {
		let id = $("#id").text();

		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp) {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	/*글수정*/
	update: function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	/*댓글쓰기*/
	replySave: function() {
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};

		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`, // 숫자 키보드 1 옆에 빽틱 ` ' 사용
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("댓글작성이 완료되었습니다.");
			//location.href = `/board/${data.boardId}`; // 다시 조회
			// NoRefresh 처리
			// 서버에서 댓글 달기 성공 했으니 화면을 다시 불러 오는 것이 아니라 
			// 서버에서 데이터먼 가지고 와서 ui 에서 자체적으로 추가한다. 
			replyAdd(resp.data); // 댓글 리스트에 추가하는 함수
			$("#reply-content").val("");  // 컨텐츠를 다시 쓸 수 있도록 비워준다.
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	/*댓글삭제*/
	// 댓글 삭제는 init에 등록하지 않고 html에서 onClick을 이용해 함수를 바로 호출해서
	// 사용할 수 있도록 했다.
	replyDelete: function(boardId, replyId) {
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(resp) {
			alert("댓글삭제 성공");
			//location.href = `/board/${boardId}`;  // 삭제 후 결과를 다시 조회
			// NoRefresh처리
			$("#reply-" + replyId).remove();  // 서버에서 삭제에 성공했으니  화면상에서도 삭제한다.
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

}

index.init();

/* NoRefresh처리 댓글 추가하기  */
// 서버에서 댓글이 저장되었으니 서버에서 내려 받은 저장에 성공한
// 데이터를 다시 받아서 화면상에서도 추가해 준다.
// 그래서 화면을 다시 읽을 필요가 없다. 
function replyAdd(data) {

	var replyItem = `<li id="reply-${data.id}" class="list-group-item d-flex justify-content-between">`;
	replyItem += `<div>${data.content}</div>`;
	replyItem += `<div class="d-flex">`;
	replyItem += `<div class="font-italic">작성자 : ${data.user.username} &nbsp;</div>`;
	replyItem += `<button onClick="index.replyDelete(${data.board.id}, ${data.id})" class="badge">삭제</button>`;
	replyItem += `</div></li>`;

	$("#reply-box").prepend(replyItem);  // prepend : 앞에 붙이다. 기존 리스트 상단에 붙이기를 해준다.
}

function back() { location.href = "/"; }








