<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="flex">
			<div>
				게시물 갯수 : <span class="badge">${articlesCount }개</span>
			</div>
			<div class="flex-grow"></div>
			<form class="flex">
				<input type="hidden" name="boardId" value=${param.boardId } />

				<select data-value="${param.searchKeywordTypeCode }" name="searchKeywordTypeCode" class="select select-bordered">
					<option disabled="disabled">검색</option>
					<option value="title">제목</option>
					<option value="body">내용</option>
					<option value="title,body">제목 + 내용</option>
				</select>


				<input name="searchKeyword" type="text" class="ml-2 w-96 input input-borderd" placeholder="검색어를 입력해주세요"
					maxlength="20" value="${param.searchKeyword }"
				/>
				<button type="submit" class="ml-2 btn btn-ghost">검색</button>
			</form>
		</div>
		<div class="table-box-type-1 mt-3">
			<table class="table table-fixed w-full">
				<colgroup>
					<col width="80" />
					<col width="140" />
					<col />
					<col width="140" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="article" items="${articles }">
						<tr class="hover">
							<td>${article.id}</td>
							<td>${article.forPrintType1RegDate}</td>
							<td>
								<a class="hover:underline block w-full truncate" href="../article/detail?id=${article.id}">${article.title}</a>
							</td>
							<td>${article.extra__writerName}</td>
							<td>${article.hitCount}</td>

						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
		<div class="page-menu mt-3 flex justify-center">
			<div class="btn-group">

				<c:set var="pageMenuLen" value="6" />
				<c:set var="startPage" value="${page - pageMenuLen >= 1 ? page- pageMenuLen : 1}" />
				<c:set var="endPage" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount}" />

				<c:set var="pageBaseUri" value="?boardId=${boardId }" />
				<c:set var="pageBaseUri" value="${pageBaseUri }&searchKeywordTypeCode=${param.searchKeywordTypeCode}" />
				<c:set var="pageBaseUri" value="${pageBaseUri }&searchKeyword=${param.searchKeyword}" />

				<c:if test="${startPage > 1}">
					<a class="btn btn-sm" href="${pageBaseUri }&page=1">1</a>
					<c:if test="${startPage > 2}">
						<a class="btn btn-sm btn-disabled">...</a>
					</c:if>
				</c:if>
				<c:forEach begin="${startPage }" end="${endPage }" var="i">
					<a class="btn btn-sm ${page == i ? 'btn-active' : '' }" href="${pageBaseUri }&page=${i }">${i }</a>
				</c:forEach>
				<c:if test="${endPage < pagesCount}">
					<c:if test="${endPage < pagesCount - 1}">
						<a class="btn btn-sm btn-disabled">...</a>
					</c:if>
					<a class="btn btn-sm" href="${pageBaseUri }&page=${pagesCount }">${pagesCount }</a>
				</c:if>
			</div>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>