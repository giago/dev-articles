<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="da-leftMenu">
	<c:if test="${not empty requestScope.article.links}" >
		<div class="da-list red">
			<div class="da-listTitle">Resources</div>
			<div class="da-listResults">
				<c:forEach items="${requestScope.article.links}" var="link">
					<div class="da-listResult">
						&raquo; <a href="<c:out value="${link.url}" />" style="color:#FF4040" target="_blank"><c:out value="${link.title}" /></a>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
	<div class="da-list">
		<jsp:include page="/fragments/ads_squared.jsp"/>
	</div>
	<div class="da-list blue">
		<div class="da-listTitle">Recent Articles</div>
		<div class="da-listResults">
			<c:forEach items="${requestScope.recentArticleUrls}" var="articleLink">
				<div class="da-listResult">
					&raquo; <a href="/article/<c:out value="${articleLink.url}" />" style="color:#009ECC"><c:out value="${articleLink.title}" /></a>
				</div>
			</c:forEach>
		</div>
	</div>
</div>