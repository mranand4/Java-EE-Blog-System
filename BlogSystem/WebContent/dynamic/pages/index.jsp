<%@ page import="java.util.ArrayList,beans.SummarisedPost" %>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
</head>

<body>
	<div id="banner">
	</div>
	
	<ul id="global-nav-bar">
		<li>
		<a href="#" style="text-decoration: underline;">Home</a>
		</li>
		<li>
		<a href="#">Projects</a>
		</li>
		<li>
		<a href="wisdom">Wisdom</a>
		</li>
		<li>
		<a href="#">Movies</a>
		</li>
		<li>
		<a href="about.html">About</a>
		</li>
		<li>
		<a href="#">More</a>
		</li>
	</ul>
	<div id="container">
		<aside>
			<div class="sidebar-box">
				<h3>Search</h3>
				<div class="sidebar-box-body">
				<form method="GET" action="#" target="_blank" style="margin: 0">
				<input type="text" placeholder="Search here ..." name="searchbox"  style="display: inline-block; width: 100%; border-radius: 0; border-color: rgb(0,49,82); " />
				<input type="submit" value="Search"style="margin-top: 4px; display: inline-block; outline: none; border: none; background-color: rgb(0,49,82); color: rgb(255,255,255);" />
				</form>
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Quotes</h3>
				<div class="sidebar-box-body">
				<q>Success is not final, failure is not fatal, it's the courage to continue which counts</q>
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Calendar</h3>
				<div class="sidebar-box-body">
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Top Posts</h3>
				<div class="sidebar-box-body">
				</div>
			</div>
			<div class="sidebar-box">
				<h3>Useful links</h3>
				<div class="sidebar-box-body">
				</div>
			</div>
		</aside>
		<section>
			<%
				for(SummarisedPost hp: (ArrayList<SummarisedPost>)request.getAttribute("posts")) {
			%>
			<div class="post">
				<%="<h1>" + hp.getTitle() + "</h1>"%>
				<div class="post-info"><span><%= hp.getDate() %></span><label> | </label><span class="author"><%= hp.getFname() + " " + hp.getLname() %></span></div>
				<%= "<p>" + hp.getSummary() + "</p>" %>
				<div class="read-more-wrapper">
					<a href="post?id=<%= hp.getPostId() %>" class="read-more" target="_blank">Read More</a>
				</div>
			</div>
			
			<% } %>
		</section>
	</div>
</body>

</html>
