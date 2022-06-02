<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}" />
		
		
		<div class="form-group">			
			<!-- id 중복 체크 -->
		<div class="d-flex justify-content-end">
			<button type="button" class="btn btn-info" onClick="idCheck()"> 중복체크</button>
		</div>
		
			<input type="text" value="${principal.user.username }" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>
		
		<%-- <c:if test="${empty principal.user.oauth}">	 --%>
			<div class="form-group">				
				<input type="password" value="${principal.user.password}" class="form-control" placeholder="Enter password" id="password">
			</div>
		<%-- </c:if> --%>
		
	
		
		<div class="form-group">			
				<!-- 주소 검색 버튼 -->
		<div class="d-flex justify-content-end">
			<!-- 타입을 버튼으로 설정해야 submit이 안됨 -->
			<button type="button" onclick="goPopup();" class="btn btn-info">주소검색</button>
		</div>		
			<input type="text" value="${principal.user.address}" class="form-control" placeholder="Enter address" id="address" >
		</div>
		
	</form>
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


