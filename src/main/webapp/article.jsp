<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="/WEB-INF/tlds/contentlanguage.tld" %>
<html>
	<head>
		<jsp:include page="/styles/public-style.css"/>
		<title><c:out value="${requestScope.article.title}" escapeXml="false"/></title>
		<script type="text/javascript" src="/js/prettify.js"></script>
		<link type="text/css" rel="stylesheet" media="screen" href="/styles/prettify.css" />
		<link rel="canonical" href="http://www.dev-articles.com/article/<c:out value="${requestScope.canonicalUrl}" escapeXml="false"/>"/>
		<meta name="description" content="<c:out value="${requestScope.article.description}" escapeXml="false"/>"></meta>
		<meta name="keywords" content="<c:out value="${requestScope.article.keywords}" escapeXml="false"/>"></meta>
		<jsp:include page="/fragments/adsense_analytics.jsp"/>
		<script type="text/javascript" src="http://apis.google.com/js/plusone.js"></script>
	</head>
	<body onload="prettyPrint()">
		<jsp:include page="/fragments/header.jsp"/>
		<jsp:include page="/fragments/left.jsp"/>
		
		<div class="da-article">
			<div class="da-articleTitle"><h1><c:out value="${requestScope.article.title}"/> <g:plusone size="medium"></g:plusone></h1></div> 
			<div class="da-writer">
				<div class="da-writer">
					Authors : <c:out value="${requestScope.article.author}"/> - Date : <c:out value="${requestScope.article.formattedCreatedDate}"/> - Views : <c:out value="${requestScope.article.counter}"/>
				</div>
			</div>
			<div class="da-tags">
				<c:forEach items="${requestScope.article.tags}" var="tag">
					<a href="/tag/<c:out value="${tag}" escapeXml="false"/>"><c:out value="${tag}" escapeXml="false"/></a>
				</c:forEach>
			</div>		
			<div class="da-articleContent">
				<c:forEach items="${requestScope.article.contents}" var="content">
					<c:choose>
						<c:when test="${content.type==0 || content.type==1}">
							<div class=<c:out value="${content.cssClass}" />>
							    <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==5 || content.type==6}">
							<div class="codeSection">
							  <pre><code class="prettyprint"><c:out value="${content.htmlData}" escapeXml="false"/></code></pre>
						  	</div>
						</c:when>
						<c:when test="${content.type==11}">
							<div class="codeSection">
							  <img src='<c:out value="${content.data}" escapeXml="false"/>' />
						  	</div>
						</c:when>
						<c:when test="${content.type==12}">
							<div class="codeSection youTube">
							  <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==10}">
							<div class="codeSection">
							  <pre><code class='prettyprint'><c:out value="${content.data}" escapeXml="false"/></code></pre>
						  	</div>
						</c:when>
						<c:when test="${content.type==13}">
							<div class="da-content-note">
							  	<span class="da-content-important-starter">Note:</span> <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==14}">
							<div class="da-content-warning">
							  	<span class="da-content-important-starter">Warning:</span> <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==15}">
							<div class="da-content-list">
								<!-- cl:trasform --><c:out value="${content.data}" escapeXml="false"/><!--/cl:trasform -->
						  	</div>
						</c:when>
						<c:when test="${content.type==16}">
							<div class="da-content-title">
							  	<c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==17}">
							<div class="da-content-summary">
							  	<c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==18}">
							<div class="da-content-important">
							  <span class="da-content-important-starter">Important:</span> <c:out value="${content.data}" escapeXml="false"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==19}">
							<div class="codeSection youTube">
							  <iframe width="560" height="349" src="http://www.youtube.com/embed/<c:out value="${content.data}" escapeXml="false"/>" frameborder="0" allowfullscreen></iframe>
						  	</div>
						</c:when>
						<c:when test="${content.type==20}">
							<div class="contentAd">
							  <jsp:include page="/fragments/ads-content.jsp"/>
						  	</div>
						</c:when>
						<c:when test="${content.type==21}"></c:when>
						<c:otherwise>
							<div class="codeSection">
								<pre><code class='prettyprint <c:out value="${content.cssClass}" />'><c:out value="${content.data}" escapeXml="false"/></code></pre>
						  	</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<jsp:include page="/fragments/comments.jsp"/>
		</div>
		<jsp:include page="/fragments/right.jsp"/>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>