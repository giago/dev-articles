<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>Developers Articles for Mobile</title>
		<meta name="viewport" content="user-scalable=no,width=device-width" />
		<meta name="description" content="This web site is a list of articles about all the aspects of development with a css for mobile" />
		<meta name="keywords" content="Development,Java,Html,AppEngine,Google,Best practices,Articles" />
		<jsp:include page="/styles/mobile-style.css"/>
	</head>
	<body onload="window.scrollBy(0,14);">
		<jsp:include page="/fragments/m_header.jsp"/>
		<div class="list">
			<div class="listTitle">
				<h2>Recent</h2>
			</div>
			<c:forEach items="${requestScope.recentArticleUrls}" var="articleLink">
				<div class="listItem" onclick="location.href='/article/<c:out value="${articleLink.url}" />'">
					<div class="listItemTitle"> 
						<a href="/article/<c:out value="${articleLink.url}" />"><c:out value="${articleLink.title}" /></a>
					</div>
					<div class="listItemDescription">
						<c:out value="${articleLink.description}" />
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="list">
			<div class="listTitle">
				<h2>Popular</h2>
			</div>
			<c:forEach items="${requestScope.popularArticleUrls}" var="articleLink">
				<div class="listItem" onclick="location.href='/article/<c:out value="${articleLink.url}" />'">
					<div class="listItemTitle"> 
						<a href="/article/<c:out value="${articleLink.url}" />"><c:out value="${articleLink.title}" /></a>
					</div>
					<div class="listItemDescription">
						<c:out value="${articleLink.description}" />
					</div>
				</div>
			</c:forEach>
		</div>
		<jsp:include page="/fragments/m_footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>
