<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.cwy.exam.demo.util.Ut"%>
<c:set var="pageTitle" value="MEMBER MODIFY" />
<%@ include file="../common/head.jspf"%>

<script>
	let MemberModify__submitDone = false;
	function MemberModify__submit(form) {
		if (MemberModify__submitDone) {
			alert('처리중입니다');
			return;
		}
		form.newLoginPw.value = form.newLoginPw.value.trim();

		if (form.newLoginPw.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호확인을 입력해주세요.');
				form.loginPwConfirm.focus();
				return;
			}
			if (form.newLoginPw.value != form.loginPwConfirm.value) {
				alert('비밀번호확인이 일치하지 않습니다.');
				form.loginPwConfirm.focus();
				return;
			}
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요');
			form.name.focus();

			return;
		}

		form.nickname.value = form.nickname.value.trim();

		if (form.nickname.value.length == 0) {
			alert('닉네임을 입력해주세요');
			form.nickname.focus();

			return;
		}

		form.cellphoneNum.value = form.cellphoneNum.value.trim();

		if (form.cellphoneNum.value.length == 0) {
			alert('전화번호를 입력해주세요');
			form.cellphoneNum.focus();

			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();

			return;
		}

		const deleteProfileImgFileInput = form["deleteFile__member__0__extra__profileImg__1"];

		if (deleteProfileImgFileInput.checked) {
			form["file__member__0__extra__profileImg__1"].vlaue = '';
		}

		const maxSizeMb = 10;
		const maxSize = maxSizeMb * 1204 * 1204;

		const profileImgFileInput = form["file__member__0__extra__profileImg__1"];

		if (profileImgFileInput.value) {
			if (profileImgFileInput.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				profileImgFileInput.focus();

				return;
			}
		}

		if (form.newLoginPw.value.length > 0) {
			form.loginPw.value = sha256(form.newLoginPw.value);
			form.newLoginPw.value = '';
			form.loginPwConfirm.value = '';
		}
		console.log("newLoginPw" + newLoginPw);
		console.log("loginPw" + loginPw);

		MemberModify__submitDone = true;
		form.submit();
	}
</script>


<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="POST" enctype="multipart/form-data" action="../member/doModify"
			onsubmit="MemberModify__submit(this); return false;"
		>
			<input type="hidden" name="memberModifyAuthKey" value="${param.memberModifyAuthKey }" />
			<input type="hidden" name="loginPw">

			<table class="table table-zebra w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>가입일</th>
						<td>${rq.loginedMember.regDate }</td>
					</tr>
					<tr>
						<th>아이디</th>
						<td>${rq.loginedMember.loginId }</td>
					</tr>
					<tr>
						<th>새 비밀번호</th>
						<td>
							<input name="newLoginPw" class="w-full input input-bordered  max-w-xs" placeholder="새 비밀번호를 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>새 비밀번호 확인</th>
						<td>
							<input name="loginPwConfirm" class="w-full input input-bordered  max-w-xs" placeholder="새 비밀번호를 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input value="${rq.loginedMember.name }" name="name" class="w-full input input-bordered  max-w-xs"
								placeholder="이름을 입력해주세요"
							/>
						</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>
							<input value="${rq.loginedMember.nickname }" name="nickname" class="w-full input input-bordered  max-w-xs"
								placeholder="닉네임을 입력해주세요"
							/>
						</td>
					</tr>
					<tr>
						<th>프로필 이미지</th>
						<td>
							<img class="w-40 h-40 object-cover" src="${rq.getProfileImgUri(rq.loginedMember.id)}" alt=""
								onerror="${rq.removeProfileImgIfNotExitOnErrorHtmlAttr}"
							/>

							<div class="mt-2">
								<label class="cursor-pointer inline-flex"> <span class="label-text mr-2 mt-1">이미지 삭제</span>
									<div>
										<input type="checkbox" name="deleteFile__member__0__extra__profileImg__1" class="checkbox" value="Y" />
									</div>
								</label>
							</div>
							<input accept="image/gif, image/jpeg, image/png" name="file__member__0__extra__profileImg__1"
								placeholder="프로필 이미지를 선택해주세요" type="file"
							/>
						</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>
							<input value="${rq.loginedMember.cellphoneNum }" name="cellphoneNum"
								class="w-full input input-bordered  max-w-xs" placeholder="전화번호를 입력해주세요"
							/>
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>
							<input value="${rq.loginedMember.email }" name="email" class="w-full input input-bordered  max-w-xs"
								placeholder="이메일을 입력해주세요"
							/>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<button class="btn btn-active btn-ghost" type="submit" value="수정">수정</button>
						</td>
					</tr>
				</tbody>

			</table>
		</form>

		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>

