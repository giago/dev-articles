<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<%User user = userService.getCurrentUser(); %>
<div class="da-centralAdvert">
<%if ((user != null && userService.isUserAdmin()) || request.getServerName().contains("localhost") || request.getServerName().contains(".dev-articles.appspot.com")) {%>
	<div style="width: 468px; height: 60px; border: 1px green solid;">space for google ads</div>	
<%} else {%>
	<script type="text/javascript"><!--
	google_ad_client = "ca-pub-2374318088795044";
	/* DevArticleCentral */
	google_ad_slot = "3803593672";
	google_ad_width = 468;
	google_ad_height = 60;
	//-->
	</script>
	<script type="text/javascript"
	src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
<% } %>
</div>