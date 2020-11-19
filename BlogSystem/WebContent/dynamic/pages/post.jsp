<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">

/* width of containers and nav bar is 640px from 0 to 720px */
/* width of containers and nav bar is 720px for 720px to 1400px; */
/* width will be 960px from 1400px to 2000px */
/* width will be 1400px from and beyond 2000px */
/* 10px gap should be there between section and aside */


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
		<a href="home">Home</a>
		</li>
	</ul>
	
	<div id="container">
		<aside>
			<div class="sidebar-box">
			<h3>Quotes</h3>
			<div class="sidebar-box-body">
			<q>Success is not final, failure is not fatal, it's the courage to continue which counts</q>
			</div>
			</div>
			<div class="sidebar-box">
			<h3>Prev/Next</h3>
			<div class="sidebar-box-body">
			<p>&#8592; Previous Post</p>
			<p>&#8594; Next Post</p>
			</div>
			</div>
		</aside>
		<section>
			<div class="post">
				<h1>${requestScope.blogPost.title}</h1>
				<div class="post-info"><span>${requestScope.blogPost.odate}</span><label>&nbsp;|&nbsp;</label><span class="author">${requestScope.blogPost.fname}&nbsp;${requestScope.blogPost.lname}</span></div>
				<p>${requestScope.blogPost.body}</p>
			</div>
		</section>
	</div>
</body>

</html>
