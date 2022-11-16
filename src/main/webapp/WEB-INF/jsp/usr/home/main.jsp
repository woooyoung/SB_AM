<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MAIN" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto">
		<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sed officiis tempore et in fugiat natus nobis
			placeat veritatis repellat cupiditate similique est impedit. Tenetur optio facere a perferendis rem quasi!</div>

		안녕하세요 <span class="Popup">팝업 예시</span>
	</div>

</section>
<div class="layer-bg">
	<div class="layer">
		<h2>POPUP</h2>
		Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam non quod ut nesciunt perferendis mollitia quo aperiam
		ex? Obcaecati velit eligendi provident officia placeat nobis laboriosam saepe sequi! Optio temporibus!
		<button class="close-btn">close</button>
	</div>
</div>
<%@ include file="../common/foot.jspf"%>