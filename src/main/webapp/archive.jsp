<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<jsp:include page="/styles/public-style.css"/>
		<title>Articles written in <c:out value="${requestScope.yearName}" /></title>
		<link rel="canonical" href="http://www.dev-articles.com/archive/<c:out value="${requestScope.yearName}" />"/>
		<jsp:include page="/fragments/adsense_analytics.jsp"/>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<div class="da-leftMenu">
			<div class="da-list">
				<jsp:include page="/fragments/ads_squared.jsp"/>
			</div>
			<div class="da-list blue">
				<div class="da-listTitle">Archive</div>
				<div class="da-listResults">
					<c:forEach items="${requestScope.yearList}" var="year">
						<div class="da-listResult">&raquo; <a href="/archive/<c:out value="${year}" />" style="color:#009ECC"><c:out value="${year}" /></a></div>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<div class="da-article">
			<div class="da-listBig">
				<div class="da-listTitleBig"><h1>Articles written in <c:out value="${requestScope.yearName}" /></h1></div>
				<div class="da-listResultsBig">
					<c:forEach items="${requestScope.archiveYearList}" var="articleLink">
						<div class="da-listResultBig">
							<div class="da-listResultBigLink"><a href="/article/<c:out value="${articleLink.url}" />"><c:out value="${articleLink.title}" /></a></div>
							<div class="da-listResultBigDescription"><c:out value="${articleLink.description}" /></div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<jsp:include page="/fragments/right.jsp"/>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>