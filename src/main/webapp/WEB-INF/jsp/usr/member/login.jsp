<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="LOGIN" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="POST" action="../member/doLogin">
			<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>아이디</th>
						<td>
							<input class="w-full input input-bordered  max-w-xs" name="loginId" type="text" placeholder="아이디를 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input class="w-full input input-bordered  max-w-xs" name="loginPw" type="text" placeholder="비밀번호를 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<button class="btn btn-active btn-ghost" type="submit" value="로그인">로그인</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<div class="container mx-auto btns">
		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
	</div>

</section>
<%@ include file="../common/foot.jspf"%>
