<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="/WEB-INF/tlds/contentlanguage.tld" %>
<html>
	<head>
		<jsp:include page="/styles/mobile-style.css"/>
		<title><c:out value="${requestScope.article.title}" escapeXml="false"/></title>
		<script type="text/javascript" src="/js/prettify.js"></script>
		<link type="text/css" rel="stylesheet" media="screen" href="/styles/prettify.css" />
		<meta name="viewport" content="user-scalable=no,width=device-width" />
		<meta name="description" content="<c:out value="${requestScope.article.description}" escapeXml="false"/>"></meta>
	</head>
	<body onload="prettyPrint();window.scrollBy(0,14);">
		<jsp:include page="/fragments/m_header.jsp"/>
		<div class="article">
			<div class="title"><h1><c:out value="${requestScope.article.title}"/></h1></div>
			<div class="writer">
				<div class="writer">
					Authors : <c:out value="${requestScope.article.author}"/> - Date : <c:out value="${requestScope.article.formattedCreatedDate}"/> - Views : <c:out value="${requestScope.article.counter}"/>
				</div>
			</div>
			<div class="tags">
				<c:forEach items="${requestScope.article.tags}" var="tag">
					<a href="/tag/<c:out value="${tag}" escapeXml="false"/>"><c:out value="${tag}" escapeXml="false"/></a>
				</c:forEach>
			</div>		
			<div class="content">
				<c:forEach items="${requestScope.article.contents}" var="content">
					<c:choose>
						<c:when test="${content.type==0 || content.type==1}">
							<div class=<c:out value="${content.cssClass}" />>
							    <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==5 || content.type==6}">
							<div class="contentCode">
							  <pre><code class="prettyprint"><c:out value="${content.htmlData}" escapeXml="false"/></code></pre>
						  	</div>
						</c:when>
						<c:when test="${content.type==11}">
							<div class="contentCode">
							  <img src='<c:out value="${content.data}" escapeXml="false"/>' />
						  	</div>
						</c:when>
						<c:when test="${content.type==12}">
							<div class="contentCode youTube">
							  <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==10}">
							<div class="contentCode">
							  <pre><code class='prettyprint'><c:out value="${content.data}" escapeXml="false"/></code></pre>
						  	</div>
						</c:when>
						<c:when test="${content.type==13}">
							<div class="contentNote">
							  	<span class="starter">Note:</span> <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==14}">
							<div class="contentWarning">
							  	<span class="starter">Warning:</span> <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==16}">
							<div class="contentTitle">
							  	<c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==17}">
							<div class="contentSummary">
							  	<c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==18}">
							<div class="contentImportant">
							  <span class="starter">Important:</span> <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==19}">
							<div class="contentCode youtube">
							  <a href="http://youtu.be/<c:out value="${content.data}" escapeXml="false"/>" class="youtubeLink">watch video</a>
						  	</div>
						</c:when>
						<c:when test="${content.type==20}"></c:when>
						<c:when test="${content.type==21}">
							<div class="contentAd">
							  <jsp:include page="/fragments/ads-content-mobile.jsp"/>
						  	</div>
						</c:when>
						<c:otherwise>
							<div class="contentCode">
								<pre><code class='prettyprint <c:out value="${content.cssClass}" />'><c:out value="${content.data}" escapeXml="false"/></code></pre>
						  	</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
		<jsp:include page="/fragments/m_footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>