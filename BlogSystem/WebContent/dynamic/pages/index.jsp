<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

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

<script type="text/javascript">

function ordinalSuffixOf(i) {
    var j = i % 10,
        k = i % 100;
    if (j == 1 && k != 11) {
        return i + "st";
    }
    if (j == 2 && k != 12) {
        return i + "nd";
    }
    if (j == 3 && k != 13) {
        return i + "rd";
    }
    return i + "th";
}

function getOrdinalDate(d) {
	
	var yy = d.substring(0, d.indexOf('-'));
	var mm = d.substring(d.indexOf('-') + 1, d.lastIndexOf('-'));
	var dd = d.substring(d.lastIndexOf('-') + 1);
	
	var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	
	var rd = ordinalSuffixOf(parseInt(dd)) + " " + months[parseInt(mm) - 1] + ", " + yy;

	return rd;
	
	
}


</script>


</head>

<body>
	<div id="banner">
	</div>
	
	<ul id="global-nav-bar">
		<li>
		<a href="#" style="text-decoration: underline;">Home</a>
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
			<c:choose>
				<c:when test = "${not empty requestScope.posts}">
					<c:forEach var="post" items="${requestScope.posts}">					
						<div class="post">
							<h1>${post.title}</h1>
							<div class="post-info"><span><script type="text/javascript">document.write(getOrdinalDate("${post.date}"));</script></span><label>&nbsp;|&nbsp;</label><span class="author">${post.fname}&nbsp;${post.lname}</span></div>
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
