<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService(); User user = userService.getCurrentUser();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
	<head>
		<title>Developer's Articles Editor</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"></meta>
	    <meta name="description" content="Developer's Articles Editor"></meta>
		<meta name="keywords" content="Developer,Articles,Editor"></meta>
		<jsp:include page="/styles/public-style.css"/>
		<jsp:include page="/styles/private-style.css"/>
		<script type="text/javascript" language="javascript" src="articleEditor/articleEditor.nocache.js"></script>
	</head>
	<body>
		<div class="da-header">
			<div class="da-headerLeft">
				<div id="gwtMainToolBar"></div>
			</div>
			<div class="da-headerRight">
		    	<%if (user != null) {%>
					 <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out <%=user.getNickname()%></a>
				<%} else {%>
					<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
				<%}%>
			</div>
		</div>
		<div class="da-mainArea">
			<div id="gwtArticleEditor"></div>
		</div>
		<div class="right">
			<div id="gwtPlugins"></div>
		</div>
	</body>
</html>