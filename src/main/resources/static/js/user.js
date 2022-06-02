/********** 회원가입 및  수정 Jquery ***********/
let index = {
	// 화살표 함수 ()=>{} 를 사용하지 않고 function(){}을 사용하는 경우
	// 반드시 this를 사용하여 현재 위치의 함수를 호출하는 것임을 명확히 해야한다.
	/*let _this = this;
	init: function() {
		$("#btn-save").on("click", function() { 
			_this.save();  // 호출 시 _this로 호출
		});*/		
	
	init: function() {
		$("#btn-save").on("click", () => { // on( 이벤트명, 실행함수 ) 
			this.save();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
	},

	/*회원가입 완료*/
	save: function() {
		alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			address: $("#address").val()
		};

		console.log(data);

		// ajax호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바스크립트 오브젝트로 변환해줌
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 서버에서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("회원가입에 실패하였습니다.");
			} else {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}

		}).fail(function(error) {
			alert(JSON.stringify(error)); // 에러도 json으로 옴
		});

	},

	/*회원정보 수정*/
	update: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			address: $("#address").val()
		};

		$.ajax({
			type: "PUT",
			url: "/api/userUpdate",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
		}).done(function(resp) {
			alert("회원수정이 완료되었습니다.");
			//console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	},
}

index.init(); // index객체의 init()함수 호출

/*********** id 중복체크 *************/
var isChecking = false; // id 중복 체크가 되어 있는 지 마지막 submit 시 체킹하기 위한 flag

function valid() {
	if (isChecking == false) {
		alert("id 중복 체크를 해주세요");
	}
	return isChecking;
}

// id 중복체크 함수
function idCheck() {
	// 서버에서 id 중복 체크 결과를 json을 받는다.
	// ajax 통신을 한다.
	// 자바스크립트에서는 중괄호{}를 이용하여 객체를 만든다. 중괄호{}로 쌓여 있으면 객체로 인식한다.
	// document.querySelector(#username)을 jquery 문법으로 바꾸면 아래와 같다.
	var username = $("#username").val();

	// id체크는 select해보는 것이기 때문에 get을 사용하지만 체크 결과를 가져와야 하기 때문에 post로 함
	// data가 "username = " + username 키 : 밸류 형태이면 contentType이 x-www-form-urlencoded
	// 받는 dataType이 json이면 자바스크립트 오브젝트로 파싱해 준다.
	$.ajax({
		type: "POST",
		url: "/user?cmd=idCheck",
		data: username,
		contentType: "text/plain; charset=utf-8",
		dataType: "text" // 응답 받을 데이터의 타입을 적으면 자바스크립트 오브젝트로 파싱해줌.
	}).done(function(data) {
		if (data === 'ok') { // 유저네임 있다는 것
			isChecking = false;
			alert('유저네임이 중복되었습니다.')
		} else {
			isChecking = true;
			$("#username").attr("readonly", "readonly");
			alert("해당 유저네임을 사용할 수 있습니다.")
		}
	});
}

/********* 주소 팝업 **********/

// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다.
// ("팝업API 호출 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function goPopup() {
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusoPopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	// 그래서 jusoPopup.jsp는 잠깐 떳다가 사라짐
	var pop = window.open("/auth/juso", "pop",
		"width=570,height=420, scrollbars=yes, resizable=yes");
}

// jusoPopup.jsp에서 opener를 이용해 이 함수를 호출하여 roadFullAddr넘겨 주면 여기서 받음
function jusoCallBack(roadFullAddr) {
	var addressEl = document.querySelector("#address");
	addressEl.value = roadFullAddr;
	//document.form.roadFullAddr.value = roadFullAddr;
}






