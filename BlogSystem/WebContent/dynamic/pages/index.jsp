<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="dynamic/includes/cal.js"></script>
<style type="text/css">

<%@ include file="../includes/header_css.css" %>

#container {
margin: 16px auto;
box-sizing: border-box;
}

#container aside {
float: right;
}

#container section {
}

.sidebar-box {
width: 100%;
box-sizing: border-box;
background-color: rgb(255,255,255);
margin-bottom: 8px;
}

.sidebar-box h3 {
font-size: 16px;
padding: 4px;
background-color: rgb(51,51,51);
color: rgb(255,255,255);
font-family: poppins-semibold;
}

.sidebar-box-body {
width: 100%;
box-sizing: border-box;
background-color: rgb(255,255,255);
padding: 4px;
}

.sidebar-box-body table {
width: 100%;
text-align: center;
}

.sidebar-box-body table td {
padding: 2px;
}

.sidebar-box-body ol {
margin: 0;
}

.post {
width: 100%;
background-color: white;
box-sizing: border-box;
padding: 8px;
margin-bottom: 8px;
}

.post h1 {
font-size: 20px;
font-family: libre-bold;
text-align: justify;
}

.post-info {
margin: 4px 0 8px 0; 
font-family: poppins-light;
font-size: 14px;
}

.post p {
font-family: poppins-regular;
text-align: justify;
}

.read-more-wrapper {
text-align: right;
width: 100%;
margin-top: 8px;
}

.read-more {
display: inline-block;
padding: 4px 8px;
color: rgb(255,255,255);
background-color: rgb(0,49,82); /* prussian color */
text-decoration: none;
font-family: poppins-semibold;
font-size: 16px;
}



</style>

<script type="text/javascript">
window.onload = function() {
	var d = new Date();
	createCalendar("cal", d.getFullYear(), d.getMonth() + 1, d.getDate());
}
</script>


</head>

<body>
	<div id="banner">
	</div>
	
	<ul id="global-nav-bar">
		<li><a href="#" style="text-decoration: underline;">Home</a></li>
		<li><a href="auth">Sign Up/In</a></li>
	</ul>
	<div id="container">
		<aside>
			<div class="sidebar-box">
				<h3>Search</h3>
				<div class="sidebar-box-body">
				<form method="GET" action="#" target="_blank" style="margin: 0">
				<input type="text" placeholder="TO BE IMPLEMENTED" name="searchbox"  style="display: inline-block; width: 100%; border-radius: 0; border-color: rgb(0,49,82); " />
				<input type="submit" value="Search"style="margin-top: 4px; display: inline-block; outline: none; border: none; background-color: rgb(0,49,82); color: rgb(255,255,255);" />
				</form>
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Daily Quote&nbsp;&nbsp;#${requestScope.dailyQuote.quoteId}</h3>
				<div class="sidebar-box-body"  style="text-align: justify;">
				<q>${requestScope.dailyQuote.quote }</q>
				<p style="font-style: italic; margin-top: 4px;">&mdash;&nbsp;${requestScope.dailyQuote.auth }</p>
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Calendar</h3>
				<div class="sidebar-box-body" id="cal">
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Most Viewed Posts</h3>
				<div class="sidebar-box-body">
					<ol>
						<c:forEach var="mvp" items="${requestScope.mvps}">
							<li><a href="post?postId=${mvp.postId}" target="_blank">${mvp.title}</a></li>
						</c:forEach>
					</ol>
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Useful links</h3>
				<div class="sidebar-box-body">
					<ol>
						<li><a href="https://pursuitofwonder.com/" target="_blank">Pursuit Of Wonder</a></li>
						<li><a href="http://www.stewardshipreport.com/himalayas-from-indiapakistan-to-bhutan-nepal-chinese-tibet/" target="_blank">Himalayan Journey</a></li>
						<li><a href="https://makefrontendshitagain.party/" target="_blank">Make Frontend Shit Again</a></li>
					</ol>
				</div>
			</div>
		</aside>
		
		<section>
			<c:choose>
				<c:when test = "${not empty requestScope.posts}">
					<c:forEach var="post" items="${requestScope.posts}">					
						<div class="post">
							<h1>${post.title}</h1>
							<div class="post-info"><span>${post.odate}</span><label>&nbsp;|&nbsp;</label><span class="author">${post.fname}&nbsp;${post.lname}</span></div>
							<p>${post.summary}</p>
							<div class="read-more-wrapper">
								<a href="post?postId=${post.postId}" class="read-more" target="_blank">Read More</a>
							</div>
						</div>
					</c:forEach>
				</c:when>		
				<c:otherwise>
					No posts yet
				</c:otherwise>
			</c:choose>	
		</section>
		
	</div>
</body>

</html>
