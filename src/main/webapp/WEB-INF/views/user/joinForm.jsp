<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<!-- onsubmit="return valid()" 속성을 걸어 주면 버튼이 눌려 질때 반드시 여기 함수를 실행한다. -->
	<form >

		<!-- id 중복 체크 -->
		<div class="d-flex justify-content-end">
			<button type="button" class="btn btn-info" onClick="idCheck()">중복체크</button>
		</div>

		<!-- required를 사용하여 값을 반드시 입력하도록 강제한다. -->
		<div class="form-group">
			<input type="text" name="username" id="username" class="form-control" placeholder="Enter username" required="required">
		</div>

		<div class="form-group">
			<input type="password" id="password" name="password" class="form-control" placeholder="Enter password" required="required">
		</div>
	

		<!-- 주소 검색 버튼 -->
		<div class="d-flex justify-content-end">
			<!-- 타입을 버튼으로 설정해야 submit이 안됨 -->
			<button type="button" onclick="goPopup();" class="btn btn-info">주소검색</button>
		</div>

		<div class="form-group">
			<!-- 주소 검색으로 입력해야 하므로 readonly 로 설정 -->
			<input type="text" name="address" id="address" class="form-control" placeholder="Enter address" required="required" readonly="readonly">
		</div>
		
	</form>
	
	<br>
	<!-- form태그를 사용하지 않고 jquery를 사용하기 위해 즉, submit이 안되게 form태그 밖으로 뺀다. -->
	<button id="btn-save" class="btn btn-primary">회원가입완료</button>

</div>



<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>


