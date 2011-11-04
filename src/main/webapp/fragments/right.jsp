<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<%User user = userService.getCurrentUser(); %>
<div class="da-rightMenu">
	<c:if test="${not empty requestScope.relatedArticles}" >
		<div class="da-list green">
			<div class="da-listTitle">Related</div>
			<div class="da-listResults">
				<c:forEach items="${requestScope.relatedArticles}" var="link">
					<div class="da-listResult">
						&raquo; <a href="/article/<c:out value="${link.url}" />" class="green" target="_blank"><c:out value="${link.title}" /></a>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
<%if ((user != null && userService.isUserAdmin()) || request.getServerName().contains("localhost") || request.getServerName().contains(".dev-articles.appspot.com")) {%>
	<div style="width: 160px; height: 600px; border: 1px green solid;">space for google ads</div>	
<%} else {%>
	<script type="text/javascript"><!--
	google_ad_client = "pub-2374318088795044";
	/* 160x600, created 2/28/10 */
	google_ad_slot = "1678203666";
	google_ad_width = 160;
	google_ad_height = 600;
	//-->
	</script>
	<script type="text/javascript"
	src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
<% } %>
</div>