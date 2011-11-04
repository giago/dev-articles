<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<%User user = userService.getCurrentUser(); %>
<%if (request.getServerName().contains("localhost") || request.getServerName().contains(".dev-articles.appspot.com")) {%>
	<div class="da-comments" style="border:1px solid red">
		-- comments --
	</div>
<%} else {%>
	<div class="da-comments">
		<!-- Include the Google Friend Connect javascript library. -->
		<script type="text/javascript" src="http://www.google.com/friendconnect/script/friendconnect.js"></script>
		<!-- Define the div tag where the gadget will be inserted. -->
		<div id="div-4412288848521432128" style="width:600px;border:1px solid #ffffff;"></div>
		<!-- Render the gadget into a div. -->
		<script type="text/javascript">
		var skin = {};
		skin['FONT_FAMILY'] = 'verdana,sans-serif';
		skin['BORDER_COLOR'] = '#ffffff';
		skin['ENDCAP_BG_COLOR'] = '#ffffff';
		skin['ENDCAP_TEXT_COLOR'] = '#009ECC';
		skin['ENDCAP_LINK_COLOR'] = '#009ECC';
		skin['ALTERNATE_BG_COLOR'] = '#ffffff';
		skin['CONTENT_BG_COLOR'] = '#ffffff';
		skin['CONTENT_LINK_COLOR'] = '#009ECC';
		skin['CONTENT_TEXT_COLOR'] = '#333333';
		skin['CONTENT_SECONDARY_LINK_COLOR'] = '#009ECC';
		skin['CONTENT_SECONDARY_TEXT_COLOR'] = '#009ECC';
		skin['CONTENT_HEADLINE_COLOR'] = '#009ECC';
		skin['DEFAULT_COMMENT_TEXT'] = 'What do you think?';
		skin['HEADER_TEXT'] = 'Comments';
		skin['POSTS_PER_PAGE'] = '5';
		google.friendconnect.container.setParentUrl('/' /* location of rpc_relay.html and canvas.html */);
		google.friendconnect.container.renderWallGadget(
		 { id: 'div-4412288848521432128',
		   site: '10037181015165158424',
		   'view-params':{"disableMinMax":"false","scope":"ID","allowAnonymousPost":"true","docId":"${requestScope.article.id}","features":"video,comment","startMaximized":"true"}
		 },
		  skin);
		</script>
	</div>
<%}%>