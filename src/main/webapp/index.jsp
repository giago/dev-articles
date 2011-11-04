<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>Dev Articles</title>
		<meta name="description" content="Dev Articles is a blog/news site with content 
		focus on software development.
		The articles range goes from Google App Engine to Google Web Toolkit, Java, 
		Javascript and many tools like maven." />
		<meta name="keywords" content="Development,Java,Html,App Engine,Google,Best practices,Articles" />
		<meta name="google-site-verification" content="oCrlH7OgbT_YZvFyMwF7jnQIIVyVOt-xGHXYfArP2TU" />
		<meta name="msvalidate.01" content="B75F9DBAD8C901F31337A224B094F287" />
		<meta name="y_key" content="8d6ece30f7fc791b" />
		<jsp:include page="/styles/public-style.css"/>
		<jsp:include page="/fragments/adsense_analytics.jsp"/>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<jsp:include page="/fragments/leftIndex.jsp"/>
		<div class="da-article">
			<div class="da-articleTitle">
				<img src="/images/logo.png" alt="Dev Articles"/>
			</div>
			<table class="da-homeTable">
				<tr>
					<td colspan="2" class="da-doubleCellHomeTable"></td>
				</tr>
				<tr>
					<td class="da-cellHomeTable">
						<div class="da-listBig">
							<div class="da-listTitleBig"><h2>Recent</h2></div>
							<div class="da-listResultsBig">
								<c:forEach items="${requestScope.recentArticleUrls}" var="articleLink">
									<div class="da-listResultBig">
										<div class="da-listResultBigLink"> 
											<a href="/article/<c:out value="${articleLink.url}" />"><c:out value="${articleLink.title}" /></a>
										</div>
										<div class="da-listResultBigDescription">
											<c:out value="${articleLink.description}" />
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</td>
					<td class="da-cellHomeTable">
						<div class="da-listBig">
							<div class="da-listTitleBig"><h2>Popular</h2></div>
							<div class="da-listResultsBig">
								<c:forEach items="${requestScope.popularArticleUrls}" var="articleLink">
									<div class="da-listResultBig">
										<div class="da-listResultBigLink"> 
											<a href="/article/<c:out value="${articleLink.url}" />"><c:out value="${articleLink.title}" /></a>
										</div>
										<div class="da-listResultBigDescription">
											<c:out value="${articleLink.description}" />
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<jsp:include page="/fragments/right.jsp"/>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>
