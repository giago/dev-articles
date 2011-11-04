<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<%User user = userService.getCurrentUser(); %>
<%if ((user != null && userService.isUserAdmin()) || request.getServerName().contains("localhost") || request.getServerName().contains(".dev-articles.appspot.com")) {%>
	<div style="width: 350px; height: 50px; border: 1px green solid;">space for google ads</div>	
<%} else {%>
	<script type="text/javascript"><!--
	  // XHTML should not attempt to parse these strings, declare them CDATA.
	  /* <![CDATA[ */
	  window.googleAfmcRequest = {
	    client: 'ca-mb-pub-2374318088795044',
	    format: '320x50_mb',
	    output: 'html',
	    slotname: '6368842287',
	  };
	  /* ]]> */
	//--></script>
	<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_afmc_ads.js"></script>
<% } %>