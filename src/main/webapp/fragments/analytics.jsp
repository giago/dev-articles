<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<%User user = userService.getCurrentUser(); %>
<%if ((user != null && userService.isUserAdmin()) || request.getServerName().contains("localhost") || request.getServerName().contains(".dev-articles.appspot.com")) {%>
	<!-- analitycs -->	
<%} else {%>
<script type="text/javascript">var _gaq = _gaq || [];_gaq.push(['_setAccount', 'UA-5190493-10']);_gaq.push(['_trackPageview']);_gaq.push(['_trackPageLoadTime']);
(function() {var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);})();</script>
<%}%>