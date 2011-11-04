<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<%User user = userService.getCurrentUser(); %>
<div class="da-leftAdvert">
<%if ((user != null && userService.isUserAdmin()) || request.getServerName().contains("localhost") || request.getServerName().contains(".dev-articles.appspot.com")) {%>
	<div style="width: 200px; height: 200px; border: 1px green solid;">space for google ads</div>	
<%} else {%>
	<script type="text/javascript"><!--
	google_ad_client = "ca-pub-2374318088795044";
	/* DevArticleLeft */
	google_ad_slot = "9408966604";
	google_ad_width = 200;
	google_ad_height = 200;
	//-->
	</script>
	<script type="text/javascript"
	src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
</script>
<% } %>
</div>